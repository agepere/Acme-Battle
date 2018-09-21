package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ReportService;
import services.ReportUpdateService;
import domain.Report;
import domain.ReportStatus;
import domain.ReportUpdate;

@Controller()
@RequestMapping("/reportUpdate")
public class ReportUpdateController extends AbstractController {

	@Autowired
	private ReportUpdateService reportUpdateService;
	@Autowired
	private ReportService reportService;
	@Autowired
	private ActorService actorService;

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int reportId,
			@RequestParam(required = false) String type) {
		ModelAndView result;
		Collection<ReportUpdate> reports;
		Report report;

		report = reportService.findOne(reportId);

		if (type == "mine") {
			reports = reportUpdateService.getReportUpdatesByActorId(reportId,
					actorService.findByPrincipal().getId());
		} else if (type == "resolved") {
			reports = reportUpdateService.getResolvedReportUpdates(reportId);
		} else {
			reports = reportUpdateService.getReportUpdatesByReportId(reportId);
		}
		result = new ModelAndView("reportUpdate/list");
		result.addObject("reportUpdates", reports);
		result.addObject("report", report);
		result.addObject("requestURI", "reportUpdate/list.do");

		return result;
	}

	@RequestMapping(value = "/listByType", method = RequestMethod.GET)
	public ModelAndView listByType(@RequestParam int reportId,
			@RequestParam(required = false) String type) {
		ModelAndView result;
		Collection<ReportUpdate> reports;
		Report report;

		report = reportService.findOne(reportId);

		if (type != null && type.equals("mine")) {
			reports = reportUpdateService.getReportUpdatesByActorId(reportId,
					actorService.findByPrincipal().getId());
		} else if (type != null && type.equals("resolved")) {
			reports = reportUpdateService.getResolvedReportUpdates(reportId);
		} else {
			reports = reportUpdateService.getReportUpdatesByReportId(reportId);
		}
		result = new ModelAndView("reportUpdate/table");
		result.addObject("reportUpdates", reports);
		result.addObject("report", report);
		result.addObject("requestURI", "reportUpdate/listByType.do");

		return result;
	}

	@RequestMapping(value = "admin/listSuspicious", method = RequestMethod.GET)
	public ModelAndView listSuspicious() {
		ModelAndView result;
		Collection<ReportUpdate> reports;

		reports = reportUpdateService.getSuspiciousReportUpdates();
		result = new ModelAndView("reportUpdate/list");
		result.addObject("reportUpdates", reports);
		result.addObject("suspiciousList", true);

		return result;
	}


	// Display ----------------------------------------------------------------
	@RequestMapping(value = "display", method = RequestMethod.GET)
	public ModelAndView display(
			@RequestParam(required = false) Integer reportUpdateId) {
		ModelAndView result;
		ReportUpdate reportUpdate;
		Report report;
		Boolean ownUpdate = false;
		Boolean myReport = false;

		report = reportService.findReportsByReportUpdate(reportUpdateId);
		reportUpdate = reportUpdateService.findOne(reportUpdateId);
		result = new ModelAndView("reportUpdate/display");
		
		if(actorService.getPrincipalAuthority().equals("PLAYER")){
			Assert.isTrue(report.getKeybladeWielder().equals(actorService.findByPrincipal()));
		}
		if (reportUpdate.getCreator().equals(actorService.findByPrincipal())) {
			ownUpdate = true;
		}else if(report.getKeybladeWielder().equals(actorService.findByPrincipal())){
			myReport = true;
		}

		result.addObject("reportUpdate", reportUpdate);
		result.addObject("report", report);
		result.addObject("ownUpdate", ownUpdate);
		result.addObject("myReport", myReport);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int reportId) {
		ModelAndView result;
		ReportUpdate reportUpdate;
		Collection<ReportUpdate> suspiciousUpdates;
		Report report;

		reportUpdate = reportUpdateService.create();
		result = createEditModelAndView(reportUpdate);
		suspiciousUpdates = reportUpdateService.getSuspiciousReportUpdatesByReportId(reportId);
		report = reportService.findOne(reportId);

		Assert.isTrue((report.getStatus() != ReportStatus.RESOLVED && report.getStatus() != ReportStatus.IRRESOLVABLE)
				|| (actorService.getPrincipalAuthority().equals("ADMIN") && suspiciousUpdates.size()>0)
				|| (actorService.getPrincipalAuthority().equals("ADMIN") && report.getStatus() != ReportStatus.RESOLVED));

		result.addObject("reportId", reportId);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int reportUpdateId,
			@RequestParam int reportId) {
		ModelAndView result;
		ReportUpdate report;

		report = reportUpdateService.findOneToEdit(reportUpdateId);
		Assert.notNull(report);
		result = createEditModelAndView(report);
		result.addObject("reportId", reportId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(ReportUpdate reportUpdate, BindingResult binding,
			@ModelAttribute("reportId") String reportId) {
		ModelAndView result = null;

		try {
			this.reportUpdateService.reconstruct(reportUpdate, binding);

			if (binding.hasErrors()) {
				result = createEditModelAndView(reportUpdate);
			} else {
				try {
					reportUpdateService.save(reportUpdate,
							new Integer(reportId));
					result = new ModelAndView(
							"redirect:/reportUpdate/list.do?reportId="
									+ reportId);

				} catch (Throwable oops) {
					String message = "error.commit";

					if (oops.getMessage().contains("error.message")
							|| oops.getMessage().contains(
									"org.hibernate.validator")) {
						message = oops.getMessage();
					}

					result = createEditModelAndView(reportUpdate, message);
				}
			}

		} catch (Throwable oops) {
			String message = "error.commit";

			if (oops.getMessage().contains("error.message")
					|| oops.getMessage().contains("org.hibernate.validator")) {
				message = oops.getMessage();
			}

			result = createEditModelAndView(reportUpdate, message);
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(ReportUpdate report, BindingResult binding) {
		ModelAndView result;

		try {
			reportUpdateService.delete(report);
			result = new ModelAndView("redirect:list.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(report, "bulletin.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/player/markSuspicious", method = RequestMethod.GET)
	public ModelAndView markSuspicious(
			@RequestParam(required = true) int reportUpdateId,
			@RequestParam(required = true) int reportId,
			@RequestParam(required = true) Boolean reportDisplay) {
		ModelAndView result = null;
		ReportUpdate reportUpdate;
		Report report;

		reportUpdate = reportUpdateService.findOne(reportUpdateId);
		report = reportService.findOne(reportId);

		try {
			Assert.isTrue(report.getKeybladeWielder().equals(actorService.findByPrincipal()),"error.message.owner");
			reportUpdateService.markSuspicious(reportUpdate);
			if(!reportDisplay){
				result = new ModelAndView(
						"redirect:/reportUpdate/display.do?reportUpdateId="
								+ reportUpdateId + "&reportId=" + reportId);
			}else{
				result = new ModelAndView(
						"redirect:/report/display.do?reportId="
								+ reportId);
			}
		} catch (Throwable oops) {
			result = createEditModelAndViewList(reportUpdate,
					"bulletin.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(ReportUpdate reportUpdate) {
		ModelAndView result;

		result = createEditModelAndView(reportUpdate, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(ReportUpdate reportUpdate,
			String message) {
		ModelAndView result;

		result = new ModelAndView("reportUpdate/edit");
		result.addObject("reportUpdate", reportUpdate);
		result.addObject("message", message);

		return result;
	}

	protected ModelAndView createEditModelAndViewList(
			ReportUpdate reportUpdate, String message) {
		ModelAndView result;

		result = new ModelAndView("reportUpdate/list");
		result.addObject("reportUpdate", reportUpdate);
		result.addObject("message", message);

		return result;
	}

}
