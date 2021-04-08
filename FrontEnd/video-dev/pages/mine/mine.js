const app = getApp();
Page({
  data: {
    "isMe": true,
    "faceUrl": "../resource/images/mine.png",
    "nickname": "jack"
  },
  logout: function() {
    var user = app.getGlobalUserInfo();
    var serverUrl = app.serverUrl;
    wx.request({
      url: serverUrl + "/user/logout?userId=" + user.id,
      method: "POST",
      header: {
        "content-type": "application/json" // default
      },
      success: function(res) {
        if (res.data.status == 200) {
          wx.showToast({
            title: "注销成功！",
            icon: "none",
            duration: 3000
          });
          wx.removeStorageSync("userInfo");
          wx.redirectTo({
            url: '../userLogin/login',
          })
        }
      }
    });
  }
})