
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.ReportService;
import services.ReportUpdateService;
import domain.Report;
import domain.ReportStatus;

@Controller()
@RequestMapping("/report")
public class ReportController extends AbstractController {

	@Autowired
	private ReportService	reportService;
	@Autowired
	private ReportUpdateService	reportUpdateService;

	@Autowired
	private ActorService	actorService;


	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false, defaultValue = "0") Integer page) {
		ModelAndView result;
		Page<Report> reports;
		Pageable pageable;

		pageable = new PageRequest(page, 5);
		reports = this.reportService.findAll(pageable);
		result = new ModelAndView("report/list");

		result.addObject("reports", reports.getContent());
		result.addObject("user", "all");
		result.addObject("page", page);
		result.addObject("requestURI", "report/list.do?page=");
		result.addObject("pageNum", reports.getTotalPages());

		return result;
	}

	@RequestMapping(value = "/listByStatus", method = RequestMethod.GET)
	public ModelAndView listByStatus(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false) String status) {
		ModelAndView result;
		Page<Report> reports;
		Pageable pageable;

		pageable = new PageRequest(page, 5);

		if (!status.equals("all"))
			reports = this.reportService.getReportsByStatus(ReportStatus.valueOf(status), pageable);
		else
			reports = this.reportService.findAll(pageable);

		result = new ModelAndView("report/table");
		result.addObject("reports", reports);
		result.addObject("user", "all");
		result.addObject("requestURI", "report/listByStatus.do?status=" + status + "&page=");
		result.addObject("page", page);
		result.addObject("pageNum", reports.getTotalPages());

		return result;
	}

	@RequestMapping(value = "/player/list", method = RequestMethod.GET)
	public ModelAndView playerList(@RequestParam(required = false, defaultValue = "0") Integer page) {
		ModelAndView result;
		Page<Report> reports;
		Pageable pageable;

		pageable = new PageRequest(page, 5);

		reports = this.reportService.findReportsByPlayer(this.actorService.findByPrincipal().getId(), pageable);
		result = new ModelAndView("report/list");
		result.addObject("requestURI", "report/player/list.do?page=");
		result.addObject("reports", reports);
		result.addObject("user", "player");
		result.addObject("page", page);
		result.addObject("pageNum", reports.getTotalPages());

		return result;
	}

	@RequestMapping(value = "/player/listByStatus", method = RequestMethod.GET)
	public ModelAndView playerListByStatus(@RequestParam(required = false) String status, @RequestParam(required = false, defaultValue = "0") Integer page) {
		ModelAndView result;
		Page<Report> reports;
		Pageable pageable;

		pageable = new PageRequest(page, 5);

		if (!status.equals("all"))
			reports = this.reportService.getReportsByStatusAndPlayer(ReportStatus.valueOf(status), this.actorService.findByPrincipal().getId(), pageable);
		else
			reports = this.reportService.findReportsByPlayer(this.actorService.findByPrincipal().getId(), pageable);

		result = new ModelAndView("report/table");
		result.addObject("requestURI", "report/player/listByStatus.do?status=" + status + "&page=");
		result.addObject("reports", reports);
		result.addObject("user", "player");
		result.addObject("page", page);
		result.addObject("pageNum", reports.getTotalPages());

		return result;
	}
	
	@RequestMapping(value = "/listMyAnsweredReports", method = RequestMethod.GET)
	public ModelAndView listMyAnswered(@RequestParam(required = false, defaultValue = "0") Integer page) {
		ModelAndView result;
		Page<Report> reports;
		Pageable pageable;

		pageable = new PageRequest(page, 5);
		reports = this.reportService.getMyAnsweredReports(actorService.findByPrincipal().getId(), pageable);
		result = new ModelAndView("report/list");

		result.addObject("reports", reports.getContent());
		result.addObject("user", "all");
		result.addObject("page", page);
		result.addObject("requestURI", "report/list.do?page=");
		result.addObject("pageNum", reports.getTotalPages());
		result.addObject("myAnswered", true);

		return result;
	}

	// Display ----------------------------------------------------------------
	@RequestMapping(value = "display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false) Integer reportId) {
		ModelAndView result;
		Report report;
		String auth;
		Boolean suspicious = false;
		
		suspicious = reportUpdateService.getSuspiciousReportUpdatesByReportId(reportId).size()>0;
		auth = this.actorService.getPrincipalAuthority();
		Assert.isTrue(auth.equals("PLAYER") || auth.equals("GM") || auth.equals("ADMIN"));

		report = this.reportService.findOne(reportId);

		if (auth.equals("PLAYER"))
			Assert.isTrue(report.getKeybladeWielder().equals(this.actorService.findByPrincipal()), "error.message.owner");

		result = new ModelAndView("report/display");

		result.addObject("report", report);
		result.addObject("suspicious", suspicious);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/player/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Report report;
		Assert.isTrue(this.actorService.getPrincipalAuthority().equals(Authority.PLAYER));

		report = this.reportService.create();
		result = this.createEditModelAndView(report);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int reportId) {
		ModelAndView result;
		Report report;

		report = this.reportService.findOne(reportId);
		Assert.notNull(report);
		result = this.createEditModelAndView(report);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Report report, BindingResult binding) {
		ModelAndView result = null;

		this.reportService.reconstruct(report, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(report);
		else
			try {

				this.reportService.save(report);
				if (this.actorService.getPrincipalAuthority().equals(Authority.PLAYER)) {
					result = new ModelAndView("redirect:player/list.do");
					result.addObject("requestURI", "player/list.do");
				} else if (this.actorService.getPrincipalAuthority().equals(Authority.GM)) {
					result = new ModelAndView("redirect:gm/list.do");
					result.addObject("requestURI", "gm/list.do");
				}

			} catch (Throwable oops) {
				String message = "error.commit";

				if (oops.getMessage().contains("error.message") || oops.getMessage().contains("org.hibernate.validator"))
					message = oops.getMessage();

				result = this.createEditModelAndView(report, message);
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Report report, BindingResult binding) {
		ModelAndView result;

		try {
			this.reportService.delete(report);
			result = new ModelAndView("redirect:list.do");
		} catch (Throwable oops) {
			result = this.createEditModelAndView(report, "bulletin.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(Report report) {
		ModelAndView result;

		result = this.createEditModelAndView(report, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Report report, String message) {
		ModelAndView result;

		result = new ModelAndView("report/edit");
		result.addObject("report", report);
		result.addObject("message", message);

		return result;
	}

}
