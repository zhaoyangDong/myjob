<table class="table table-hover att-table" id="${'${fid}_list'}"
       style="border: 1px solid #ccc; margin-bottom: 0px; border-bottom: none">
    <tr class='active' style="font-size: 15px; color: black;">
        <td style="padding-left: 30px">${filename}</td>
        <td>${filetype}</td>
        <td>${filesize}</td>
        <td>${upload}</td>
        <td></td>
    </tr>
<#list file as f>
    <tr>
        <td style="padding-left: 30px">
            <a href="${contextPath}/sys/attach/file/detail?fileId=${f.fileId}&&token=${f._token}">${f.fileName}</a></td>
        <td style="width: 20%">${f.fileType}</td>
        <script>
            var html = "<td>" + Hap.bytestosize(${f.fileSize}) + "</td>";
            document.write(html);
        </script>
        <td style="width: 20%">${f.uploadDate?string('yyyy-MM-dd HH:mm:ss')}</td>
        <td>
            <#if enableRemove=true>
                <button class="btn btn-danger" id="${f.fileId}" _token="${f._token}">${delete}</button>
            </#if>
        </td>
    </tr>
</#list>
</table>
<#if enableUpload=true>
<input type="file" name="files" id="${fid}">
</#if>
<script>

    //删除按钮实现
    <#if enableRemove=true>
    $("#${fid}_list").click(function (e) {
        if ($(e.target)[0].className == 'btn btn-danger') {
            kendo.ui.showConfirmDialog({
                title: $l('hap.tip.info'),
                message: $l('hap.tip.delete_confirm')
            }).done(function (event) {
                if (event.button == 'OK') {
                    $.ajax({
                        type: "post",
                        url: "${contextPath}/sys/attach/file/delete",
                        contentType: "application/json",
                        data: JSON.stringify({
                            fileId: $(e.target).attr('id'),
                            _token: $(e.target).attr('_token')
                        }),
                        success: function (result) {
                            if (result.success) {
                                var ids = result.rows[0].fileId;
                                ($('#' + ids).parents("tr")).remove();
                                kendo.ui.showInfoDialog({
                                    message: $l('hap.mesg_delete')
                                });
                            } else {
                                kendo.ui.showErrorDialog({
                                    message: result.message
                                });
                            }
                        }
                    })
                }
            })
        }
    });
    </#if>
    <#if enableUpload=true>
    $("#${fid}").kendoUpload({
        async: {
            saveUrl: "${contextPath}/sys/attach/upload"
        },
        showFileList: false,
        validation: {
            <#if type??>
                allowedExtensions: '${type}'.split(";"),
            </#if>
            <#if size??>
                maxFileSize: ${size}
            </#if>},
        validate: function (e) {
            var files = e.files;
            var that = this;
            $.each(files, function () {
                if (this.validationErrors && (this.validationErrors).length > 0) {
                    kendo.ui.showInfoDialog({
                        title: '提示信息',
                        message: that.localization[this.validationErrors[0]]
                    });
                }
            })
        },
        upload: function (e) {
            e.data = {
                sourceType: '${sourceType}',
                sourceKey: '${sourceKey}'
            }
        },

        success: function (e) {
            //状态为成功
            if (e.response.success === true) {
                var file = "<tr>" +
                        "<td style='padding-left:30px'>" +"<a  href='${contextPath}/sys/attach/file/detail?fileId=" + e.response.file.fileId + "&&token=" + e.response.file._token + "'>" + e.response.file.fileName + "</td>" +
                        "<td style='width: 20%'>" + e.response.file.fileType + "</td>" +
                        "<td>" + Hap.bytestosize(e.response.file.fileSize) + "</td>" +
                        "<td style='width: 20%'>" + Hap.formatDateTime(new Date(e.response.file.uploadDate)) + "</td>" +
                                "<#if enableRemove==true>"+
                            "<td >" + "<button class='btn btn-danger' id='" + e.response.file.fileId + "'_token='" + e.response.file._token + "'>" + "${delete}" + "</button>" +
                                    "</#if>" +
                        "</tr>";
                $("#${fid}_list").append(file);
            }
            kendo.ui.showInfoDialog({
                message: e.response.message
            })
        }
    })
    </#if>
</script>
