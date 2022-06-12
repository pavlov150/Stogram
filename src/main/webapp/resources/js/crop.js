var cropper;
$(document).ready(function(){
//cropper Start
    $('#imgdiv').hide();
    $('#croppedimg').hide();

    $('#image').on("change", function (e) {
        console.log(e);
        ratio = 1 / 1;
        croppingimg(e, ratio);
    });

    function croppingimg(e, ratio) {
        var imgsrc = URL.createObjectURL(e.target.files[0])
        if (imgsrc) {
            // console.log(imgsrc);
            $('#imagecan').attr("src", imgsrc);
            $('#imgdiv').show();
            var image = document.getElementById('imagecan');
            if (cropper) {
                cropper.destroy();
            }
            cropper = new Cropper(image, {
                viewMode: 3,
                aspectRatio: ratio,
                dragMode: 'move',
                cropBoxMovable: false,
                cropBoxResizable: false,
                checkOrientation: false,
                viewMode:1,
                crop: function (event) {
                    var url = cropper.getCroppedCanvas({
                        width: 100,
                        height: 100,
                    }).toDataURL('image/png', 1.0);
                    $('#cropped').attr("src", url);
                    $('#croppedimg').show();
                    window.URL.revokeObjectURL(imgsrc);
                }
            });
        }
    }
    //cropper end
});

function onClickUpload() {
    //console.log('test')
    var profileImage;
    if(cropper.getCroppedCanvas()){
        cropper.getCroppedCanvas().toBlob(function (blob) {
            // profileImage = new FormData();
            // profileImage.append('profileImage', blob, "profileImage");
            var url = cropper.getCroppedCanvas().toDataURL();
            console.log(url);
            // $('.showloading').show();
            // $.ajax({
            //     url: "{!!url('user/insertimage')!!}",
            //     data: profileImage,
            //     type: "POST",
            //     processData: false,
            //     contentType: false,
            //     headers: {
            //         'X-CSRF-TOKEN': "{{ csrf_token() }}"
            //     },
            //     success: function (result) {
            // $('.showloading').hide();
            if (cropper) {
                cropper.destroy();
                $('#croppedimg').hide();
                $('#imgdiv').hide();
            }
            // if (result['code'] == 200) {
            $('#profileImageModal').modal('toggle');
            $('#profilePicture').removeAttr('src')
            $('#profilePicture').attr('src', url);
            $('#blob-img').val(url);
            // $('#headerProfileImage').attr('src', url);
            $('#image').val("");
            // toastr.success("Image Upadated");
            // } else {
            //     alert(result['code'] + ":" + result['massage'])
            //     $('#profileImageModal').modal('toggle');
            //     // toastr.error("Something went wrong. Please try again");
            // }
            // },
            // error: function (jqXHR, textStatus, errorThrown) {
            //      $('.showloading').hide();
            //     console.log(jqXHR);
            //     toastr.error("Something went wrong. Please try again");
            //     // alert(jqXHR.status + ":" + textStatus + ":" + errorThrown);
            // }
            // });
        }, 'image/png');
    }
}