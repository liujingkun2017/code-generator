package org.liujk.code.generator.jeecg.modules.user.service.impl;

import org.liujk.code.generator.jeecg.modules.user.entity.UserInfo;
import org.liujk.code.generator.jeecg.modules.user.mapper.UserInfoMapper;
import org.liujk.code.generator.jeecg.modules.user.service.IUserInfoService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: user_info
 * @author： jeecg-boot
 * @date：   2020-12-03
 * @version： V1.0
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

}
