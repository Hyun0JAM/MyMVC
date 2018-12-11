package memo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import memo.model.MemoDAO;

public class MemoBlindAction extends AbstractController {

	@Override
	public void excute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		MemoDAO memodao = new MemoDAO();
		String[] checkedIdx = req.getParameterValues("check");
		for(String idx : checkedIdx) {
			if(idx!=null) memodao.setMemoBlind(idx);
		}
		super.setRedirect(true);
		super.setViewPage("myMemoList.do");
	}
}
