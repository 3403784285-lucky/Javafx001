package common;

/**
 * 消息类型，用于展示各种操作状态和结果
 */
public interface MessageType {
    /**
     * 表示登录成功的消息类型。
     */
    String MESSAGE_LOGIN_SUCCEED="1";//表示登陆成功
    /**
     * 表示登录失败的消息类型。
     */
    String MESSAGE_LOGIN_FAIL="2";//表示登录失败
    /**
     * 表示修改密码成功的消息类型。
     */
    String MESSAGE_MODIFY_SUCCEED="3";//修改密码成功
    /**
     * 表示修改密码失败的消息类型。
     */
    String MESSAGE_MODIFY_FAIL="4";//修改密码失败
    /**
     * 表示注册成功的消息类型。
     */
    String MESSAGE_REGISTER_SUCCEED="5";//注册成功
    /**
     * 表示注册失败的消息类型。
     */
    String MESSAGE_REGISTER_FAIL="6";//注册失败；
    /**
     * 表示修改资料成功的消息类型。
     */
    String MESSAGE_PROFILE_SUCCEED="7";//修改资料成功
    /**
     * 表示修改资料失败的消息类型。
     */
    String MESSAGE_PROFILE_FAIL="8";//修改资料失败
    /**
     * 表示搜索成功的消息类型。
     */
    String MESSAGE_SEARCH_SUCCEED="9";
    /**
     * 表示搜索失败的消息类型。
     */
    String MESSAGE_SEARCH_FAIL="10";
    /**
     * 表示好友申请成功的消息类型。
     */
    String MESSAGE_APPLY_SUCCEED1="11";

    /**
     * 表示好友申请失败的消息类型。
     */
    String MESSAGE_APPLY_FAIL1="12";
    /**
     * 表示好友申请消息的消息类型。
     */
    String MESSAGE_FRIEND_APP="13";
    /**
     * 表示好友关系消息的消息类型。
     */
    String MESSAGE_FRIEND_FRI="14";
    /**
     * 表示陌生人关系消息的消息类型。
     */
    String MESSAGE_FRIEND_STRANGER="15";



}
