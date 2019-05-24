$(function () {
    var shopId = getQueryString("shopId");
    var isEdit = shopId ? true : false;
    var initUrl = '/shopadmin/getshopinitinfo';
    var registerShopUrl = '/shopadmin/registershop';
    var shopInfoUrl = "/shopadmin/getshopbyid?shopId=" + shopId;
    var editShopUrl = '/shopadmin/modifyshop';
    $.toast('shopoperation.js中获取Id为：' + shopId);
    if (!isEdit){
        getShopInitInfo();
    }else {
        getShopInitInfo(shopId);
    }
    // 修改：从后台获取传入shopId的区域信息和店铺分类信息
    function getShopInitInfo(shopId) {
        $.getJSON(shopInfoUrl, function (data) {
            if (data.success) {
                var shop = data.shop;
                $('#shop-name').val(shop.shopName);
                $('#shop-addr').val(shop.shopAddr);
                $('#shop-phone').val(shop.phone);
                $('#shop-desc').val(shop.shopDesc);
                var shopCategory = '<option data-id="'
                    + shop.shopCategory.shopCategoryId + '" selected>'
                    + shop.shopCategory.shopCategoryName + '</option>'
                var tempAreaHtml = '';
                data.areaList.map(function (item, index) {
                    tempAreaHtml += '<option data-id="' + item.areaId + '">'
                        + item.areaName + '</option>';
                });
                //店铺类别不能修改
                $('#shop-category').html(shopCategory);
                $('#shop-category').attr('disabled', 'disabled');
                $('#shop-area').html(tempAreaHtml);
                $("#shop-area option[data-id='"+shop.area.areaId+"']").attr("selected","selected");
            }
        });
        // 注册：从后台获取区域信息和店铺分类信息
        function getShopInitInfo() {
            $.getJSON(initUrl, function (data) {
                if (data.success) {
                    var tempHtml = '';
                    var tempAreaHtml = '';
                    data.shopCategoryList.map(function (item, index) {
                        tempHtml += '<option data-id="' + item.shopCategoryId + '">'
                            + item.shopCategoryName + '</option>';
                    });
                    data.areaList.map(function (item, index) {
                        tempAreaHtml += '<option data-id="' + item.areaId + '">'
                            + item.areaName + '</option>';
                    });
                    $('#shop-category').html(tempHtml);
                    $('#shop-area').html(tempAreaHtml);
                }
            });
            //获取表单信息发送给后台
            $('#submit').click(function (data) {
                var shop = {};
                if (isEdit) {
                    shop.shopId = shopId;
                }
                shop.shopName = $('#shop-name').val();
                shop.shopAddr = $('#shop-addr').val();
                shop.phone = $('#shop-phone').val();
                shop.shopDesc = $('#shop-desc').val();

                shop.shopCategory = {
                    shopCategoryId: $('#shop-category').find('option').not(function () {
                        return !this.selected;
                    }).data('id')
                };
                shop.area = {
                    areaId: $('#shop-area').find('option').not(function () {
                        return !this.selected;
                    }).data('id')
                };

                var shopImg = $('#shop-img')[0].files[0];
                var formData = new FormData();
                formData.append('shopImg', shopImg);
                formData.append('shopStr', JSON.stringify(shop));
                var verifyCodeActual = $('#j_captcha').val();
                if (!verifyCodeActual) {
                    $.toast('请输入验证码！');
                    return;
                }
                formData.append('verifyCodeActual', verifyCodeActual);
                $.ajax({
                    url: (isEdit ? editShopUrl : registerShopUrl),
                    type: 'POST',
                    data: formData,
                    contentType: false,
                    processData: false,
                    cache: false,
                    success: function (data) {
                        if (data.success) {
                            $.toast('提交成功！');
                        } else {
                            $.toast('提交失败!' + data.errMsg);
                        }
                        $('#captcha_img').click();
                    }
                    // dataType:"json"
                });

            });
        }
    }
})