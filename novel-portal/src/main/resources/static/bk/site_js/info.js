var info = new Vue({
    el: '#app',
    data: {
        size: novel.size,
        id: novel.id,
        first: 0,
        last: 0,
        currPage: 1,
        list: null,
        content: '',
        rigAdv: true,
        nloading: false,
        cloading: false
    },
    mounted() {
        var _self = this;
        var novelId = this.id;
        _self.nloading = true;
        _self.cloading = true;
        $.ajax({
            type: "post",
            async: true,
            url: "/chapter/list/" + novelId,
            success: function(data){
                _self.list = data;
                _self.currPage = data[0].id;
                _self.first = data[0].id;
                _self.last = data[data.length - 1].id;
                _self.cloading = false;
                //获取内容
                _self.changeInfo(_self.currPage);
            },
            error: function () {
                _self.$message({
                    message: "系统错误，请稍后重试。",
                    type: "warning"
                });
            }
        });
        $("body").css("display", "block");
    },
    methods: {
        pre() {
            var page = this.currPage;
            this.currPage = page > this.first ? page - 1 : this.first;
            this.changeInfo(this.currPage);
            $(window).scrollTop(0);
        },
        next() {
            var page = this.currPage;
            this.currPage = page < this.last ? page + 1 : this.last;
            this.changeInfo(this.currPage);
            $(window).scrollTop(0);
        },
        share() {
            this.$message({
                message: '功能未开通，程序员小哥哥正在努力实现，请期待！',
                type: 'warning'
            });
        },
        rigClose(){
            this.rigAdv = false;
            $(".noveInfo").css("min-height", "750px");
        },
        changeInfo(chapterId){
            var _self = this;
            var novelId = this.id;
            this.currPage = chapterId;
            $.ajax({
                type: "post",
                async: true,
                url: "/chapter/content/" + novelId + "/" + chapterId,
                success: function(data){
                    _self.content = data;
                    _self.nloading = false;
                },
                error: function () {
                    _self.$message({
                        message: "系统错误，请稍后重试。",
                        type: "warning"
                    });
                }
            });
        }
    }
});

$(document).ready(function(){
    $("body").on("click", "#list a", function (){
        var allElem = $("#list a");
        for(var i = 0; i < allElem.length; i++){
            allElem[i].className="";
        }
        $(this).addClass("active");
    })
});
