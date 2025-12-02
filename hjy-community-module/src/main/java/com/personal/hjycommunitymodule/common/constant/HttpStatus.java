package com.personal.hjycommunitymodule.common.constant;

/**
 * @ClassName HttpStatus
 * @Author liupanpan
 * @Date 2025/12/2
 * @Description 返回状态码
 */
public class HttpStatus {
    /*操作成功*/
    private static final Integer SUCCESS = 200;
    /*对象创建成功*/
    private static final Integer CREATED = 201;
    /*请求已经被接受*/
    private static final Integer ACCEPTED = 202;
    /*操作已经执行成功，但是没有返回数据*/
    private static final Integer NO_CONTENT = 204;
    /*资源已经被移除*/
    private static final Integer MOVED_PERM = 301;
    /*重定向*/
    private static final Integer SEE_OTHER = 303;
    /** * 资源没有被修改 */
    public static final int NOT_MODIFIED = 304;
    /** * 参数列表错误（缺少，格式不匹配） */
    public static final int BAD_REQUEST = 400;
    /** * 未授权 */
    public static final int UNAUTHORIZED = 401;
    /** * 访问受限，授权过期 */
    public static final int FORBIDDEN = 403;
    /** * 资源，服务未找到 */
    public static final int NOT_FOUND = 404;
    /** * 不允许的http方法 */
    public static final int BAD_METHOD = 405;
    /** * 资源冲突，或者资源被锁 */
    public static final int CONFLICT = 409;
    /** * 不支持的数据，媒体类型 */
    public static final int UNSUPPORTED_TYPE = 415;
    /** * 系统内部错误 */
    public static final int ERROR = 500;
    /** * 接口未实现 */
    public static final int NOT_IMPLEMENTED = 501;
}
