package com.oscar.springbootstudy.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

//    @Resource
//    private SystemDbProperties systemDbProperties;
//
//    /**
//     * lo4j2日志系统
//     */
//    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
//
//    /**
//     * @Fields cacheManager
//     * @Description: 缓存管理
//     * @date 2017年8月18日 上午1:17:23
//     * @author dev sandy
//     */
//    @Autowired
//    protected EhCacheCacheManager cacheManager;
//
//
//    /**
//     * 获取系统数据库编码
//     * @return
//     */
//    protected String getDbCode(){
//        return systemDbProperties.getDbCode();
//    }
//
//    /**
//     * 获取环境编码
//     * @return
//     */
//    protected String getSystemCode(){
//        return systemDbProperties.getSystemCode();
//    }
}
