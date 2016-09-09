package cn.starteasy.uaa.security;

/**
 * TODO 一句话描述该类用途
 * <p>
 * 创建时间: 16/9/4 下午9:50<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
public class Base64Test {
    public static void main(String[] args) {
        System.out.println(new sun.misc.BASE64Encoder().encode("seuaaapp:seuaaapp".getBytes()));
    }
}
