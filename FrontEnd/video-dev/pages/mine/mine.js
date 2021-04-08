const app = getApp();
Page({
  data: {
    "isMe": true,
    "faceUrl": "../resource/images/mine.png",
    "nickname": "jack"
  },
  changeFace : function() {
    var me = this;
    wx.chooseImage({
      count: 1,
      sizeType: ['compressed'],
      sourceType: ['album'],
      success: function(res) {
        console.log(res);
        var filePath = res.tempFilePaths[0];
        wx.showLoading({
          title: "上传中..."
        });
        var user = app.getGlobalUserInfo();
        var serverUrl = app.serverUrl;
        var minioUrl = app.minioUrl;
        wx.uploadFile({
          filePath: filePath,
          name: 'file',
          url: serverUrl + "/user/updateFace",
          header: {
            accessToken: user.userToken
          },
          formData: {
            userId: user.id
          },
          success: function(res) {
            console.log(res);
            var data = JSON.parse(res.data);
            wx.hideLoading();
            if (data.status == 200) {
              wx.showToast({
                title: "更改头像成功！",
                icon: "success",
                duration: 3000
              });
              var faceUrl = data.data;
              console.log(minioUrl + faceUrl);
              me.setData({
                "faceUrl": minioUrl + faceUrl
              });
            } else if (data.status == 500) {
              wx.showToast({
                title: data.msg,
                icon: "none",
                duration: 3000
              });
            } else if (data.status == 502) {
              wx.redirectTo({
                url: '../userLogin/login',
              })
            }
          }
        })
      }
    })
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