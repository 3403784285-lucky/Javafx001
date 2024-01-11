package common;

/**
 * 消息类型
 */
public interface MessageType {
    String MESSAGE_LOGIN_SUCCEED="1";//表示登陆成功
    String MESSAGE_LOGIN_FAIL="2";//表示登录失败
    String MESSAGE_MODIFY_SUCCEED="3";//修改密码成功
    String MESSAGE_MODIFY_FAIL="4";//修改密码失败
    String MESSAGE_REGISTER_SUCCEED="5";//注册成功
    String MESSAGE_REGISTER_FAIL="6";//注册失败；
    String MESSAGE_PROFILE_SUCCEED="7";//修改资料成功
    String MESSAGE_PROFILE_FAIL="8";//修改资料失败
    String MESSAGE_SEARCH_SUCCEED="9";
    String MESSAGE_SEARCH_FAIL="10";
    String MESSAGE_APPLY_SUCCEED1="11";
    String MESSAGE_APPLY_FAIL1="12";
    String MESSAGE_FRIEND_APP="13";
    String MESSAGE_FRIEND_FRI="14";
    String MESSAGE_FRIEND_STRANGER="15";



}
