package com.bw.movie.receiver;

import android.content.Context;

import com.tencent.android.tpush.XGLocalMessage;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

import java.util.HashMap;

/**
 * @author : FangShiKang
 * @date : 2019/02/20.
 * email : fangshikang@outlook.com
 * desc :       信鸽
 */
public class MessageReceiver extends XGPushBaseReceiver {
    @Override
    public void onRegisterResult(Context context, int i, XGPushRegisterResult xgPushRegisterResult) {

    }

    @Override
    public void onUnregisterResult(Context context, int i) {

    }

    @Override
    public void onSetTagResult(Context context, int i, String s) {

    }

    @Override
    public void onDeleteTagResult(Context context, int i, String s) {

    }

    @Override
    public void onTextMessage(Context context, XGPushTextMessage xgPushTextMessage) {
        //新建本地通知
        XGLocalMessage local_msg = new XGLocalMessage();
        //设置本地消息类型，1:通知，2:消息
        local_msg.setType(1);
        // 设置消息标题
        local_msg.setTitle("qq");
        //设置消息内容
        local_msg.setContent("ww");
        //设置消息日期，格式为：20140502
        local_msg.setDate("20140930");
        //设置消息触发的小时(24小时制)，例如：22代表晚上10点
        local_msg.setHour("19");
        //获取消息触发的分钟，例如：05代表05分
        local_msg.setMin("31");
        //设置消息样式，默认为0或不设置
        local_msg.setBuilderId(0);
        //设置动作类型：1打开activity或app本身，2打开浏览器，3打开Intent ，4通过包名打开应用
        local_msg.setAction_type(2);
        //设置拉起应用页面
        local_msg.setActivity("com.qq.xgdemo.SettingActivity");
        // 设置URL
        local_msg.setUrl("http://www.baidu.com");
        // 设置Intent
        local_msg.setIntent("intent:10086#Intent;scheme=tel;action=android.intent.action.DIAL;S.key=value;end");
        // 是否覆盖原先build_id的保存设置。1覆盖，0不覆盖
        local_msg.setStyle_id(1);
        // 设置音频资源
        local_msg.setRing_raw("mm");
        // 设置key,value
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("key", "v1");
        map.put("key2", "v2");
        local_msg.setCustomContent(map);
        // 设置下载应用URL
        local_msg.setPackageDownloadUrl("http://softfile.3g.qq.com:8080/msoft/179/1105/10753/MobileQQ1.0(Android)_Build0198.apk");
        //添加通知到本地
        XGPushManager.addLocalNotification(context, local_msg);
    }

    @Override
    public void onNotifactionClickedResult(Context context, XGPushClickedResult xgPushClickedResult) {

    }

    @Override
    public void onNotifactionShowedResult(Context context, XGPushShowedResult xgPushShowedResult) {

    }
}
