const app = getApp();
Page({
  data: {
    "isMe": true,
    "faceUrl": "../resource/images/mine.png",
    "nickname": "jack",
    "fansCounts": 0,
    "followCounts": 0,
    "receiveLikeCounts": 0,
  },
  onLoad : function() {
    // 页面渲染后执行
    var me = this;
    var user = app.getGlobalUserInfo();
    var serverUrl = app.serverUrl;
    var minioUrl = app.minioUrl;
    wx.request({
      url: serverUrl + "/user/queryUserInfo?userId=" + user.id,
      method: "GET",
      header: {
        "content-type": "application/json" // default
      },
      success: function(res) {
        if (res.data.status == 200) {
          var userInfo = res.data.data;
          var nickname = userInfo.nickname;
          var fansCounts = userInfo.fansCounts;
          var followCounts = userInfo.followCounts;
          var receiveLikeCounts = userInfo.receiveLikeCounts;
          var faceUrl = "../resource/images/mine.png";
          if (userInfo.faceImage != null && userInfo.faceImage != '' && userInfo.faceImage != undefined) {
            faceUrl = minioUrl + userInfo.faceImage;
          }
          me.setData({
            "faceUrl": faceUrl,
            "nickname": nickname,
            "fansCounts": fansCounts,
            "followCounts": followCounts,
            "receiveLikeCounts": receiveLikeCounts,
          });
        } else if (res.data.status == 500) {
          wx.showToast({
            title: res.data.msg,
            icon: "none",
            duration: 3000
          });
        }
      }
    });
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
  },
  uploadVideo: function() {
    wx.chooseVideo({
      sourceType: ['album'],
      success: function (res) {
        console.log(res);
        var duration = res.duration;
        var height = res.height;
        var width = res.width;
        var size = res.size;
        var tempFilePath = res.tempFilePath;
        var thumbTempFilePath = res.thumbTempFilePath;

        if (duration > 10) {
          wx.showToast({
            title: "视频时间不能超过30秒！",
            icon: "none",
            duration: 3000
          });
        } else if (duration < 1) {
          wx.showToast({
            title: "视频时间不能短于1秒！",
            icon: "none",
            duration: 3000
          });
        } else {
          wx.redirectTo({
            url: "../chooseBgm/chooseBgm?duration=" + duration
            + "&height=" + height
            + "&width=" + width
            + "&size=" + size
            + "&tempFilePath=" + tempFilePath
            + "&thumbTempFilePath=" + thumbTempFilePath
          })
        }
      }
    });
  }
})