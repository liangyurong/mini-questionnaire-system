package com.lyr.qs.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlus插件
 * @author yurong333
 * @since 2023-01-10
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 官方建议：使用多插件应遵循以下添加顺序
     *  1、多租户，动态表名
     *  2、分页、乐观锁
     *  3、sql性能优化，防止全表更新和删除
     *  总结：对sql单次改造的优先放入，不对sql进行改造的最后放入
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

}