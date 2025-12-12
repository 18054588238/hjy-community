package com.personal.hjycommunitymodule.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName CorsConfig
 * @Author liupanpan
 * @Date 2025/12/12
 * @Description
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /*
    * 配置“全局”跨域请求处理。配置的 CORS 映射将应用于 带注解的控制器、函数式端点和静态资源。
    * 带注解的控制器可以通过 `@CrossOrigin` 进一步声明更细粒度的配置。
    * 在这种情况下，此处声明的“全局”CORS 配置将与控制器方法上定义的本地 CORS 配置合并。
    * */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                // 为指定的路径模式启用跨域请求处理。 支持精确路径映射 URI（例如“/admin”）以及 Ant 风格的路径模式（例如“/admin/**”）。
                // 默认情况下，此映射的 CorsConfiguration 将使用 CorsConfiguration.applyPermitDefaultValues() 中描述的默认值进行初始化。
                .addMapping("/**") // 配置哪些请求需要启用跨域请求处理
                // 此方法替代了 `allowedOrigins(String...)`，它支持更灵活的模式，用于指定允许浏览器发起跨域请求的来源。
                // 有关格式详情和其他注意事项，请参阅 `CorsConfiguration.setAllowedOriginPatterns(List)`。
                //默认情况下，此设置未启用。
                .allowedOriginPatterns("*")// 指定发起跨域请求的来源，被指定的路径允许跨域请求访问
                // 浏览器是否应将凭据（例如 Cookie）与跨域请求一起发送到指定端点。配置值设置在预检请求的 Access-Control-Allow-Credentials 响应头中。
                //注意：此选项会与已配置的域建立高度信任关系，但同时也会增加 Web 应用程序的攻击面，因为它会暴露敏感的用户特定信息，例如 Cookie 和 CSRF 令牌。
                //默认情况下，此选项未设置，因此 Access-Control-Allow-Credentials 标头也未设置，凭据将无法使用。
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                // 设置预检请求中允许在实际请求中使用的标头列表。
                //可以使用特殊值“*”来允许所有标头。
                //如果标头名称属于 CORS 规范中的以下类型之一，则无需列出：Cache-Control、Content-Language、Expires、Last-Modified 或 Pragma。
                //默认情况下，所有标头均被允许。
                .allowedHeaders("*")
                // 配置客户端可以缓存预检请求响应的时长（以秒为单位）。
                //默认值为 1800 秒（30 分钟）。
                .maxAge(3600); // 1h

    }
}
