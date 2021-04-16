var vm = new Vue({
    el:'#deptdiv',
    data:{
        deptlist:[],
        searchEntity:{},
        pageNum:1,
        pageSize:3,
        page:{},
        plist:[],
        entity:{},
        postids:[]
    },
    methods:{
        getDeptListConn:function () {
            var _this = this;
            axios.post("../dept/getDeptListConn.do?pageNum="+_this.pageNum+"&pageSize="+_this.pageSize,_this.searchEntity).then(function (response) {
                _this.pageNum = response.data.currentPage;
                _this.deptlist = response.data.list;
                _this.pageSize = response.data.pageSize;
                _this.page = response.data;
            })
        },
        paging:function (pageNum) {
            this.pageNum = pageNum;
            this.getDeptListConn();
        },
        toDeptPost:function (id) {
            var _this = this;
            axios.get("../dept/getDeptByDeptId.do?id="+id).then(function (response) {
                _this.entity = response.data;
                _this.plist = response.data.plist;
                _this.postids = response.data.postids;
                document.getElementById("deptpostdiv").style.display="block";
            })
        },
        saveDeptPost:function () {
            var _this = this;
            console.log(_this.postids);
            axios.post("../dept/saveDeptPost.do?id="+_this.entity.id,_this.postids).then(function (response) {
                if (response.data.flag){
                    document.getElementById("deptpostdiv").style.display="none";
                    this.getDeptListConn();
                }else{
                    alert(response.data.msg);
                }
            })
        }
    }
});
vm.getDeptListConn();