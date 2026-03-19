package com.nhnacademy.shoppingmall.controller.point;


import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.point.domain.PointHistory;
import com.nhnacademy.shoppingmall.point.repository.impl.PointHistoryRepositoryImpl;
import com.nhnacademy.shoppingmall.point.service.PointHistoryService;
import com.nhnacademy.shoppingmall.point.service.impl.PointHistoryServiceImpl;
import com.nhnacademy.shoppingmall.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PointHistoryController{
    private final PointHistoryService pointHistoryService;

    @Autowired
    public PointHistoryController(PointHistoryService pointHistoryService) {
        this.pointHistoryService = pointHistoryService;
    }

    @RequestMapping(value = "/mypage/pointHistory.do", method = RequestMethod.GET)
    public String execute(Model model, HttpSession session,
                          @RequestParam(name = "page", defaultValue = "1") int page) {
        User user = (User) session.getAttribute("user");
        if(user == null) {
            model.addAttribute("message", "세션이 만료되었습니다.");
            return "common/message";
        }

        int pageSize = 10;
        Page<PointHistory> pointHistoryPage = pointHistoryService.getPointHistoryPage(user.getUserId(), page, pageSize);
        long totalCount = pointHistoryPage.getTotalCount();
        long totalPages = (totalCount > 0) ? (totalCount + pageSize - 1) / pageSize : 1;

        model.addAttribute("pointHistoryPage", pointHistoryPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "shop/mypage/point_history";
    }
}
