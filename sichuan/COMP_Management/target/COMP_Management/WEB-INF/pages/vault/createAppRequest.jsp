<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>


<div id="spanCreate" class="float-div" style="display: none;">
    <table>
            <tr>
                <td valign="top">授权条件：</td>
                <td>
                    <div>
                    <input name="authmode"id="auth_num" type="radio" value="1" checked="true">按次数授权(默认次数一次)
                    <input name="authmode"id="auth_time_id" type="radio" value="hour">按时间段授权
                        <select id="auth_time_val">
                            <option value="1">1小时</option>
                            <option value="4">4小时</option>
                            <option value="12">12小时</option>
                            <option value="24">24小时</option>
                        </select>
                    </div>
                </td>
            </tr>
        <tr>
            <td valign="top">授权模式：</td>
            <td>
                <div>
                    <select id="auth_mode">
                    </select>
                </div>
            </td>
        </tr>
        <tr>
            <td valign="top">审批人：</td>
            <td>
                <div>
                    <select id="approver">
                    </select>
                </div>
            </td>
        </tr>
        <tr>
            <td valign="top">申请原因：</td>
            <td><textarea style="width: 183px; height: 138px;"
                          onmousedown="s(event,this)" name="application_reason" id="application_reason">
				</textarea></td>
        </tr>
        <span id="requestID" style="display: none"></span>
    </table>
</div>
<div id="localAuth" class="page-form" style="display: none;">
    <div class="field">
        <div class="caption">本次申请/审批时限截止：</div>
        <div class="content">
            <span id="timeoutlocal"></span>
        </div>
        <div class="point"></div>
        <div class="content">
            <h3 id="timeoutleftlocal"></h3>
        </div>
        <div class="point"></div>
    </div>
    <div class="field">
        <div class="caption">审批状态：</div>
        <div class="content">
            <span style="color: red" id="auth_status_local"></span>
        </div>
        <div class="point"></div>
    </div>
    <div class="field">
        <div class="caption">申请人账号：</div>
        <div class="content">
            <input type="text" size="28" value="" name="appli_account"
                   id="appli_account" />
        </div>
        <div class="point"></div>
    </div>
    <div class="field">
        <div class="caption">申请人密码：</div>
        <div class="content">
            <input type="text" size="28" value="" name="appli_pwd"
                   id="appli_pwd" />
        </div>
        <div class="point"></div>
    </div>
</div>

<div id="remoteAuth" class="page-form" style="display: none;">
    <div class="field">
        <div class="caption">申请/审批截止时间：</div>
        <div class="content">
            <span id="timeout"></span>
        </div>
        <div class="point"></div>
        <div class="content">
            <h3 id="timeoutleft"></h3>
        </div>
        <div class="point"></div>
    </div>

    <div class="field">
        <div class="caption">审批状态：</div>
        <div class="content">
            <span style="color: red" id="auth_status">    </span>
            <button onclick="applyStatusRefresh()">刷新</button>
        </div>
        <div class="point"></div>
    </div>
    <div class="field">
        <div class="caption">短信授权码：</div>
        <div class="content">
            <input type="text" size="28" value="" name="authorization_code"
                   id="authorization_code" />

            <button id = "reSendJKPass" onclick="reSendJKPass()">重发</button>

        </div>
        <div class="content">
            <span id="reSendJKPassText"></span>
        </div>
        <div class="point"></div>
    </div>
    <span id="remoterequestID" style="display: none"></span>
</div>