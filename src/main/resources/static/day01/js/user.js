var vue = new Vue({
    el: "#app",
    data: {
        user: {id:"",username:"aaa",password:"",age:"",sex:"",email:""},
        userList: []
    },
    methods: {
        findAll: function () {

            axios.get("/user/user").then(response => {
                var _this = this;
                console.log(response);
            _this.userList=    response.data.data
            }).catch(function (err) {
                console.log(err);
            });
        },
        findById: function (userid) {
            var _this = this;
            axios.get("/user/user/"+userid).then(response =>  {
              _this.user = response.data.data;
                $('#myModal').modal("show");
            }).catch(function (err) {
            });

        },
        update: function (user) {
            var _this = this;
            axios.put("/user/user/"+_this.user.id,_this.user).then(function (response) {
                _this.findAll();
            }).catch(function (err) {
            });
        }
    },
    created(){
        this.findAll();
    }
});