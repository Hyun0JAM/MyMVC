package myshop.controller;

import java.util.*;
import javax.servlet.http.*;
import common.controller.AbstractController;
import member.model.MemberVO;
import myshop.model.ProductDAO;

public class ProductRegisterAction extends AbstractController {

   @Override
   public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
      //1.반드시 관리자로 로그인 했을 경우에만 접근이 가능하도록 한다.
      MemberVO loginuser = super.getLoginUser(req);
      if(loginuser == null) return;
      if(!"admin".equals(loginuser.getUserid())) {//관리자로 로그인 하지 않았을때 
         req.setAttribute("msg", "관리자만 접근이 가능합니다.");
         req.setAttribute("loc", "javascript:history.back();");
         super.setViewPage("/WEB-INF/msg.jsp");
         return;
      }
      ProductDAO productdao = new ProductDAO();
      List<HashMap<String,Object>> categoryHashList = productdao.getAllCategory();
      req.setAttribute("categoryHashList", categoryHashList);
      List<HashMap<String,Object>> specHashList = productdao.getAllSpec();
      req.setAttribute("specHashList", specHashList);
      super.setRedirect(false);
      super.setViewPage("/WEB-INF/myshop/admin/productRegister.jsp");
   }// end of execute()---
}