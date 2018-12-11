package myshop.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import common.controller.AbstractController;
import myshop.model.InterProductDAO;
import myshop.model.ProductDAO;
import myshop.model.ProductVO;

public class MallDispalyJSONAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String start = req.getParameter("start");
		String len = req.getParameter("len");
		String pspec = req.getParameter("pspec");
		
		if(start==null|| start.trim().isEmpty()) {
			start="1";
		}
		if(len==null|| len.trim().isEmpty()) {
			len="8";
		}
		if(pspec==null|| pspec.trim().isEmpty()) {
			pspec="NEW";
		}
		int startRno = Integer.parseInt(start);
		int endRno = startRno+Integer.parseInt(len)-1;
		InterProductDAO pdao = new ProductDAO();
		List<ProductVO> productList = pdao.getProductByPspec(pspec,startRno,endRno);
		JSONArray jsonArray = new JSONArray();
		if(productList != null && productList.size() > 0) {
			for(ProductVO pvo :productList) {
				JSONObject jobj = new JSONObject();
				//JSONObject는 JSON(key:value)형태의 데이터를 관리해주는 클래스
				jobj.put("pnum", pvo.getPnum());
				jobj.put("pname", pvo.getPname());
				jobj.put("pcategory_fk", pvo.getPcategory_fk());
				jobj.put("pcompany", pvo.getPcompany());
				jobj.put("pimage1", pvo.getPimage1());
				jobj.put("pimage2", pvo.getPimage2());
				jobj.put("pqty", pvo.getPqty());
				jobj.put("price", pvo.getPrice());
				jobj.put("saleprice", pvo.getSaleprice());
				jobj.put("pspec", pvo.getPspec());
				jobj.put("pcontent", pvo.getPcontent());
				jobj.put("point", pvo.getPoint());
				jobj.put("pinputdate", pvo.getPinputdate());
				
				jsonArray.add(jobj);
			}
		}
		String str_jsonArray = jsonArray.toString();
		req.setAttribute("productList", productList);
		req.setAttribute("str_jsonArray",str_jsonArray );
		super.setViewPage("/WEB-INF/myshop/mallDisplayJSON.jsp");
	}

}
