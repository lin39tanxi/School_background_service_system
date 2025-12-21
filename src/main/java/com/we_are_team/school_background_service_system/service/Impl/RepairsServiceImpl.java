package com.we_are_team.school_background_service_system.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.we_are_team.school_background_service_system.context.BaseContext;
import com.we_are_team.school_background_service_system.mapper.RepairsMapper;
import com.we_are_team.school_background_service_system.mapper.UserMapper;
import com.we_are_team.school_background_service_system.pojo.dto.RepairEvaluationCreateDTO;
import com.we_are_team.school_background_service_system.pojo.entity.RepairOrder;
import com.we_are_team.school_background_service_system.pojo.entity.User;
import com.we_are_team.school_background_service_system.pojo.vo.GetRepairOrderListVO;
import com.we_are_team.school_background_service_system.pojo.vo.RepairOrderVO;
import com.we_are_team.school_background_service_system.result.PageResult;
import com.we_are_team.school_background_service_system.service.ReparisService;
import com.we_are_team.school_background_service_system.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
@Slf4j
@Service
public class RepairsServiceImpl implements ReparisService {
    @Autowired
    private RepairsMapper repairsMapper;
    @Autowired
    private AliOssUtil aliOssUtil;
    @Autowired
    private UserMapper userMapper;
/**
 * 用户提交报修单
 */

    @Override
    public void submitRepair(String description, String address,MultipartFile[] imageUrlsArray,String phone,LocalDateTime appointmentBegin,LocalDateTime appointmentEnd) {
        User user = userMapper.getUserByUserId(BaseContext.getCurrentId());
        if(user == null){
            throw new RuntimeException("用户不存在");
        }
        if(!user.getPermission().contains("0")){
            throw new RuntimeException("你不是用户，无法进行提交报单");
        }
        RepairOrder repairOrder = new RepairOrder();
            repairOrder.setUserId(BaseContext.getCurrentId());
            repairOrder.setDescription(description);
            repairOrder.setCreatedTime(LocalDateTime.now());
            repairOrder.setProcessStatus(0);
            repairOrder.setPhone(phone);
            repairOrder.setAddress(address);
            repairOrder.setAppointmentBegin(appointmentBegin);
            repairOrder.setAppointmentEnd(appointmentEnd);
        List<String> urlsArray = new ArrayList<>();
        if(imageUrlsArray ==  null || imageUrlsArray.length == 0){
            repairsMapper.insert(repairOrder);
            return;
        }
        else
        if ( imageUrlsArray.length> 0  && !imageUrlsArray[0].getOriginalFilename().equals("")) {
            log.info("图片数组{}",imageUrlsArray[0].getOriginalFilename());
            for (MultipartFile file : imageUrlsArray) {
                String originalFilename = file.getOriginalFilename();
                String objectName = originalFilename.substring(originalFilename.lastIndexOf("."));
                String obj = UUID.randomUUID().toString() + objectName;
                String url = null;
                try {
                    url = aliOssUtil.upload(file.getBytes(), obj);
                    urlsArray.add(url);
                } catch (IOException e) {
                    throw new RuntimeException("上传失败");
                }
            }
            String result = String.join(",", urlsArray);
            log.info("图片字符串{}",result);
            repairOrder.setImageUrls(result);
        }
            repairsMapper.insert(repairOrder);

        }
/**
 * 获取我的报修单
 */
    @Override
    public PageResult getMyRepairs(String  status, Integer pageNum, Integer pageSize, String orderKey, LocalDate beginTime,LocalDate endTime) {
        User user = userMapper.getUserByUserId(BaseContext.getCurrentId());
        if(!user.getPermission().contains("0")){
            throw new RuntimeException("这是用户端查看自己的报修列表");
        }
        //        开启分页查询
        PageHelper pageHelper = new PageHelper();
        pageHelper.startPage(pageNum, pageSize);
        Integer userId = BaseContext.getCurrentId();
        Page<GetRepairOrderListVO> repairOrders  = repairsMapper.getMyRepairs(userId,status,orderKey,beginTime,endTime);
        repairOrders.getResult().forEach(repairOrder -> {
            if(repairOrder.getImageUrl()!= "" && repairOrder.getImageUrl() != null){
                   String[] imageUrls =  repairOrder.getImageUrl().split(",");
                   repairOrder.setImageUrl(imageUrls[0]);
            }
        });
        return new PageResult(repairOrders.getTotal(),repairOrders.getResult());
    }
/**
 * 用户获取报修单详情
 */
    @Override
    public RepairOrderVO getRepairOrdeDetail(Integer orderId) {
       RepairOrder repairOrder = repairsMapper.getRepairDetail(orderId);
       if(repairOrder == null){
           throw new RuntimeException("没有这个保修单号");
       }
       if(!repairOrder.getUserId().equals(BaseContext.getCurrentId())){
           throw new RuntimeException("你没有这个权限查询这个保修单");
       }
       if(repairOrder.getImageUrls() == null || repairOrder.getImageUrls().equals("")){
           RepairOrderVO repairOrderVO =  RepairOrderVO.builder().
                   orderId(repairOrder.getOrderId())
                   .adminId(repairOrder.getAdminId())
                   .comment(repairOrder.getComment())
                   .createdTime(repairOrder.getCreatedTime())
                   .processStatus(repairOrder.getProcessStatus())
                   .rejectReason(repairOrder.getRejectReason())
                   .updatedTime(repairOrder.getUpdatedTime())
                   .description(repairOrder.getDescription())
                   .completedTime(repairOrder.getCompletedTime())
                   .address(repairOrder.getAddress())
                   .phone(repairOrder.getPhone())
                   .appointmentBegin(repairOrder.getAppointmentBegin())
                   .appointmentEnd(repairOrder.getAppointmentEnd())
                   .build();
           return repairOrderVO;
       }
       else {
           String[] urlArray=  repairOrder.getImageUrls().split(",");
           List<String> urls = new ArrayList<>(Arrays.asList(urlArray));
           RepairOrderVO repairOrderVO =  RepairOrderVO.builder().
                   orderId(repairOrder.getOrderId())
                   .adminId(repairOrder.getAdminId())
                   .comment(repairOrder.getComment())
                   .createdTime(repairOrder.getCreatedTime())
                   .processStatus(repairOrder.getProcessStatus())
                   .rejectReason(repairOrder.getRejectReason())
                   .updatedTime(repairOrder.getUpdatedTime())
                   .description(repairOrder.getDescription())
                   .completedTime(repairOrder.getCompletedTime())
                   .address(repairOrder.getAddress())
                   .phone(repairOrder.getPhone())
                   .imageUrls(urls).build();
           return repairOrderVO;
       }
    }






    /**
     * 取消保修单
     */
    @Override
    public void cancelRepair(Integer orderId) {
        User user = userMapper.getUserByUserId(BaseContext.getCurrentId());
        if(!user.getPermission().contains("0")){
            throw new RuntimeException("这是用户端取消保修单");
        }
        Integer processStatus = 5; // 状态码为5表示用户取消了保修单
        RepairOrder repairOrder = new RepairOrder();
        repairOrder.setProcessStatus(processStatus);
        repairOrder.setUpdatedTime(LocalDateTime.now());
        repairOrder.setCompletedTime(LocalDateTime.now());
        repairOrder.setOrderId(orderId);
        repairsMapper.updateRepairStatus(repairOrder);


    }

    /**
     * 评价保修单
     * @param orderId
     * @param repairEvaluationCreateDTO
     */
    @Override
    public void commentRepair(Integer orderId, RepairEvaluationCreateDTO repairEvaluationCreateDTO) {
        User user = userMapper.getUserByUserId(BaseContext.getCurrentId());
        if(!user.getPermission().contains("0")){
            throw new RuntimeException("这是用户端评价保修单");
        }
        Integer processStatus = 4;
        LocalDateTime commentCreatedTime = LocalDateTime.now();
        String comment = repairEvaluationCreateDTO.getComment();
        Integer rating = repairEvaluationCreateDTO.getRating();
        repairsMapper.updateComment(orderId,comment,rating,commentCreatedTime,processStatus);

    }

    /**
     * 用户删除评价
     * @param orderId
     */
    @Override
    public void deleteComment(Integer orderId) {
        User user = userMapper.getUserByUserId(BaseContext.getCurrentId());
        String permission = user.getPermission();
        if(!permission.contains("0")){
            throw new RuntimeException("你无权限删除这个保修单评价");
        }
        repairsMapper.deleteComment(orderId);
    }

    /**
     * 管理员获取我的报修单
     * @param status
     * @param pageNum
     * @param pageSize
     * @param orderKey
     * @param beginTime
     * @param endTime
     * @return
     */
    @Override
    public PageResult AdminGetMyRepairs(String status, Integer pageNum, Integer pageSize, String orderKey, LocalDate beginTime, LocalDate endTime) {
       User user = userMapper.getUserByUserId(BaseContext.getCurrentId());
       if(!user.getPermission().contains("2")){
           throw new RuntimeException("你没有权限获得报修单列表");
       }
        PageHelper pageHelper = new PageHelper();
        pageHelper.startPage(pageNum, pageSize);
        Integer userId = null;
        Page<GetRepairOrderListVO> repairOrders  = repairsMapper.getMyRepairs(userId,status,orderKey,beginTime,endTime);
        repairOrders.getResult().forEach(repairOrder -> {
            if(repairOrder.getImageUrl()!= "" && repairOrder.getImageUrl() != null){
                String[] imageUrls =  repairOrder.getImageUrl().split(",");
                repairOrder.setImageUrl(imageUrls[0]);
            }
        });
        return new PageResult(repairOrders.getTotal(),repairOrders.getResult());
    }

    /**
     * 管理员获取报修单详情
     * @param orderId
     * @return
     */
    @Override
    public RepairOrderVO AdmingetRepairOrdeDetail(Integer orderId) {
        User user =userMapper.getUserByUserId(BaseContext.getCurrentId());
        String permission = user.getPermission();
        log.info("权限：{}",permission);
        if(!permission.contains("2")){
            throw new RuntimeException("管理员你没有这个权限获得保修单详情");
        }
        RepairOrder repairOrder = repairsMapper.getRepairDetail(orderId);
        if(repairOrder == null){
            throw new RuntimeException("没有这个保修单号");
        }

        if(repairOrder.getImageUrls() == null || repairOrder.getImageUrls().equals("")){
            RepairOrderVO repairOrderVO =  RepairOrderVO.builder().
                    orderId(repairOrder.getOrderId())
                    .adminId(repairOrder.getAdminId())
                    .comment(repairOrder.getComment())
                    .createdTime(repairOrder.getCreatedTime())
                    .processStatus(repairOrder.getProcessStatus())
                    .rejectReason(repairOrder.getRejectReason())
                    .updatedTime(repairOrder.getUpdatedTime())
                    .description(repairOrder.getDescription())
                    .completedTime(repairOrder.getCompletedTime())
                    .appointmentBegin(repairOrder.getAppointmentBegin())
                    .appointmentEnd(repairOrder.getAppointmentEnd())
                    .build();
            return repairOrderVO;
        }
        else {
            String[] urlArray=  repairOrder.getImageUrls().split(",");
            List<String> urls = new ArrayList<>(Arrays.asList(urlArray));
            RepairOrderVO repairOrderVO =  RepairOrderVO.builder().
                    orderId(repairOrder.getOrderId())
                    .adminId(repairOrder.getAdminId())
                    .comment(repairOrder.getComment())
                    .createdTime(repairOrder.getCreatedTime())
                    .processStatus(repairOrder.getProcessStatus())
                    .rejectReason(repairOrder.getRejectReason())
                    .updatedTime(repairOrder.getUpdatedTime())
                    .description(repairOrder.getDescription())
                    .completedTime(repairOrder.getCompletedTime())
                    .imageUrls(urls).build();
            return repairOrderVO;
        }
    }

    @Override
    public void updateRepairStatus(Integer orderId) {
        User user = userMapper.getUserByUserId(BaseContext.getCurrentId());
        if(!user.getPermission().contains("2")){
            throw new RuntimeException("你没有权限接受这个报修单");
        }

        RepairOrder repairOrder = new RepairOrder();
        repairOrder.setProcessStatus(2);
        repairOrder.setOrderId(orderId);
        repairOrder.setUpdatedTime(LocalDateTime.now());
        repairOrder.setAdminId(BaseContext.getCurrentId());
        repairsMapper.updateRepairStatus(repairOrder);
    }
/**
 * 拒绝报修单
 */
    @Override
    public void regiectRepair(Integer orderId, String rejectReason) {
        User user = userMapper.getUserByUserId(BaseContext.getCurrentId());
        if(!user.getPermission().contains("2")){
            throw new RuntimeException("你没有权限拒绝这个报修单");
        }
        RepairOrder repairOrder = new RepairOrder();
        repairOrder.setProcessStatus(4);
        repairOrder.setOrderId(orderId);
        repairOrder.setRejectReason(rejectReason);
        repairOrder.setAdminId(BaseContext.getCurrentId());
        repairOrder.setCompletedTime(LocalDateTime.now());
        repairsMapper.updateRepairStatus(repairOrder);


    }

    @Override
    public void completeRepair(Integer orderId) {
        User user = userMapper.getUserByUserId(BaseContext.getCurrentId());
        if(!user.getPermission().contains("2")){
            throw new RuntimeException("你没有权限完成这个报修单");
        }
        RepairOrder repairOrder = new RepairOrder();
        repairOrder.setProcessStatus(3);
        repairOrder.setOrderId(orderId);
        repairOrder.setAdminId(BaseContext.getCurrentId());
        repairOrder.setCompletedTime(LocalDateTime.now());
        repairsMapper.updateRepairStatus(repairOrder);
    }

}