
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PrizeService;
import domain.Prize;

@Controller
@RequestMapping("/prize")
public class PrizeController extends AbstractController {

	@Autowired
	private PrizeService	prizeService;


	@RequestMapping("/list")
	public ModelAndView list(@RequestParam(required = false, defaultValue = "0") Integer page) {
		ModelAndView res;
		Page<Prize> prizes;
		Pageable pageable;

		pageable = new PageRequest(page, 5);

		prizes = this.prizeService.getMyPrizesPageable(pageable);

		res = new ModelAndView("prize/list");
		res.addObject("prizes", prizes.getContent());
		res.addObject("page", page);
		res.addObject("requestURI", "prize/list.do?page=");
		res.addObject("pageNum", prizes.getTotalPages());

		return res;
	}

	@RequestMapping("/open")
	public ModelAndView open(@RequestParam Integer prizeId) {
		ModelAndView res;
		Prize p = this.prizeService.findOne(prizeId);

		if (p == null)
			res = new ModelAndView("redirect:list.do");
		else
			try {
				this.prizeService.open(p);
				res = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				res = new ModelAndView("redirect:list.do");
			}

		return res;
	}

	@RequestMapping(value = "/openAJAX", method = RequestMethod.GET)
	public ModelAndView openAJAX(@RequestParam Integer prizeId, @RequestParam(required = false, defaultValue = "0") Integer page) {
		ModelAndView res;
		Prize prize;
		Page<Prize> prizes;
		Pageable pageable;

		pageable = new PageRequest(page, 5);

		prize = this.prizeService.findOne(prizeId);

		if (prize == null)
			res = new ModelAndView("prize/prizes");
		else
			try {
				this.prizeService.open(prize);
				res = new ModelAndView("prize/prizes");
			} catch (Throwable oops) {
				res = new ModelAndView("prize/prizes");
			}

		prizes = this.prizeService.getMyPrizesPageable(pageable);
		res.addObject("prizes", prizes.getContent());
		res.addObject("page", page);
		res.addObject("requestURI", "prize/list.do?page=");
		res.addObject("pageNum", prizes.getTotalPages());

		return res;
	}

}
