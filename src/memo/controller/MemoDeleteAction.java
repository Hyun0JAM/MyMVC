package memo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import memo.model.MemoDAO;

public class MemoDeleteAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		MemoDAO memodao = new MemoDAO();
		String[] checkArr = req.getParameterValues("check");
		for(String idx:checkArr) {
			if(idx!=null) memodao.deleteMemo(idx);
		}
		if("admin".equals(super.getLoginUser(req).getUserid())) super.setViewPage("memoList.do");
		else super.setViewPage("myMemoList.do");
	}
}
