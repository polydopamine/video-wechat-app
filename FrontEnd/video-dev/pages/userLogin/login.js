const app = getApp();
Page({
  data: {

  },
  doLogin: function(e) {
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
        url: serverUrl + "/user/login",
        method: "POST",
        data: {
          username: username,
          password: password
        },
        header: {
          "content-type": "application/json"
        },
        success: function(res) {
          if (res.data.status == 200) {
            wx.showToast({
              title: "登陆成功！",
              icon: "none",
              duration: 3000
            });
            app.setGlobalUserInfo(res.data.data);
            console.log(app.getGlobalUserInfo());
            wx.redirectTo({
              url: '../mine/mine',
            })
          } else if (res.data.status == 500) {
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
  goRegisterPage: function() {
    wx.redirectTo({
      url: '../userRegist/regist',
    })
  }
  
})