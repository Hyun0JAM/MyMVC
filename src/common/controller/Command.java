package common.controller;

import javax.servlet.http.*;

public interface Command {
	// 
	void excute(HttpServletRequest req, HttpServletResponse res) throws Exception;
}
