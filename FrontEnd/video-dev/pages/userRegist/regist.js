const app = getApp();

Page({
  data: {

  },
  doRegister: function(e) {
    var formObject = e.detail.value;
    var username = formObject.username;
    var password = formObject.password;
    if (username.length == 0 || password.length == 0) {
      wx.showToast({
        title: "用户名或密码不能为空",
        icon: "none",
        duration: 3000
      });
    } else {
      var serverUrl = app.serverUrl;
      wx.request({
        url: serverUrl + "/user/register",
        method: "POST",
        data: {
          username: username,
          password: password
        },
        header: {
          "content-type": "application/json"
        },
        success: function(res) {
          var status = res.data.status;
          
          if (status == 200) {
            wx.showToast({
              title: "注册成功！",
              icon: "none",
              duration: 3000
            });
            // 设置全局变量
            app.setGlobalUserInfo(res.data.data);
            // 页面跳转
            wx.redirectTo({
              url: '../mine/mine',
            })
          } else if (status == 500) {
            wx.showToast({
              title: res.data.msg,
              icon: "none",
              duration: 3000
            });
          }
        }
      });
    }
  },
  goLoginPage: function() {
    wx.redirectTo({
      url: '../userLogin/login',
    })
  }
})