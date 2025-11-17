import { useLoginUserStore } from "./stores/useLoginUserStore";
import { message } from "ant-design-vue";
import router from "./router";


let firstLoginUser = true;
router.beforeEach( async(to, from, next) => { 
  const loginUserStore= useLoginUserStore();
  let loginUser= loginUserStore.loginUser;
  if(firstLoginUser){
    await loginUserStore.fetchLoginUser();
    loginUser= loginUserStore.loginUser;
    firstLoginUser= false;
  }

  const toUrl= to.fullPath;
  if(toUrl.startsWith('/admin')){
    if(!loginUser || loginUser.userRole !== "admin"){
      message.error("无权限访问，请先登录管理员账号");
      next(`/user/login?redirect=${to.fullPath}`);
      return;
    }
  }
  next(); 
});