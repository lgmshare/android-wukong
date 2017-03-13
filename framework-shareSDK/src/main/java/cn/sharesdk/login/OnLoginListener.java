package cn.sharesdk.login;

/**
 * 第三方登录操作过程中会回调这个接口中的方法，不同方法衔接第
 * 三方登录与用户应用登录/注册的逻辑，故使用第三方登录时一定要实
 * 现本接口的不同方法，否则第三方登录是没有意义的。
 */
public interface OnLoginListener {

    /**
     * 登录完成调用此接口，返回登录者在第三方社交平台上的用户数据。
     */
    void onSuccess(String platform, ShareUser shareUser);

    /**
     * 登录失败
     */
    void onFailure(String msg);

    void onCancel();
}
