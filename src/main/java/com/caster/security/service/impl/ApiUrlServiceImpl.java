package com.caster.security.service.impl;

import com.caster.security.entity.ApiUrl;
import com.caster.security.mapper.ApiUrlMapper;
import com.caster.security.service.ApiUrlService;
import com.github.yulichang.base.MPJBaseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系統RestApi 服务实现类
 * </p>
 *
 * @author Caster
 * @since 2022-07-05
 */
@Service
@RequiredArgsConstructor
public class ApiUrlServiceImpl extends MPJBaseServiceImpl<ApiUrlMapper, ApiUrl> implements ApiUrlService {
}
