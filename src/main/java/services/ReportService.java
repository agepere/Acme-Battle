
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ReportRepository;
import domain.Actor;
import domain.KeybladeWielder;
import domain.Report;
import domain.ReportStatus;

@Service
@Transactional
public class ReportService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ReportRepository	reportRepository;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private Validator			validator;


	// CRUD methods

	public Report create() {
		Report report;

		report = new Report();
		report.setPhotos(new ArrayList<String>());

		return report;
	}

	public Report save(final Report report) {
		Assert.notNull(report);

		Report saved;

		if (report.getId() == 0) {
			Assert.isTrue(this.actorService.getPrincipalAuthority().equals("PLAYER"));
			Assert.isTrue(this.actorService.findByPrincipal().equals(report.getKeybladeWielder()));
			Assert.isTrue(report.getStatus().equals(ReportStatus.ONHOLD));

			if (report.getPhotos() != null) {
				Assert.isTrue(report.getPhotos().size() <= 5, "error.message.photos.limit");
				final UrlValidator urlValidator = new UrlValidator(UrlValidator.ALLOW_LOCAL_URLS);

				for (final String url : report.getPhotos())
					Assert.isTrue(urlValidator.isValid(url), "org.hibernate.validator.constraints.URL.message");
			}
		} else {
			Assert.isTrue(this.actorService.getPrincipalAuthority().equals("PLAYER") || this.actorService.getPrincipalAuthority().equals("GM") || this.actorService.getPrincipalAuthority().equals("ADMIN"));
			if(this.actorService.getPrincipalAuthority().equals("PLAYER")){
				Assert.isTrue(this.actorService.findByPrincipal().equals(report.getKeybladeWielder()));
			}
			Assert.isTrue(report.getStatus() != ReportStatus.ONHOLD);

		}

		saved = this.reportRepository.save(report);

		return saved;
	}

	public Report findOne(final int ReportId) {
		Assert.notNull(ReportId);

		Report Report;

		Report = this.reportRepository.findOne(ReportId);

		return Report;
	}

	public Collection<Report> findAll() {
		Collection<Report> Reports;

		Reports = this.reportRepository.findAll();

		return Reports;
	}
	
	public Page<Report> findAll(Pageable p) {
		Page<Report> reports;

		reports = this.reportRepository.findAll(p);

		return reports;
	}

	public void delete(final Report report) {
		Assert.notNull(report);

		this.reportRepository.delete(report);
	}

	//Other business methods

	public Collection<Report> findReportsByPlayer(final int playerId) {
		return this.reportRepository.findReportsByPlayer(playerId);
	}

	public Report findReportsByReportUpdate(final int reportUpdateId) {
		return this.reportRepository.findReportsByReportUpdate(reportUpdateId);
	}

	public Collection<Report> getReportsByStatus(final ReportStatus status) {
		return this.reportRepository.getReportsByStatus(status);
	}

	public Collection<Report> getReportsByStatusAndPlayer(final ReportStatus status, final int playerId) {
		return this.reportRepository.getReportsByStatusAndPlayer(status, playerId);
	}

	public Report reconstruct(final Report report, final BindingResult binding) {

		if (report.getId() == 0) {
			Actor actor;

			actor = this.actorService.findByPrincipal();
			report.setDate(new Date(System.currentTimeMillis() - 1000));
			report.setKeybladeWielder((KeybladeWielder) actor);
			report.setStatus(ReportStatus.ONHOLD);

		} else {
			final Report original = this.findOne(report.getId());

			report.setContent(original.getContent());
			report.setDate(original.getDate());
			report.setIsBug(original.getIsBug());
			report.setKeybladeWielder(original.getKeybladeWielder());
			report.setPhotos(original.getPhotos());
			report.setReportUpdates(original.getReportUpdates());
			report.setTitle(original.getTitle());
		}

		this.validator.validate(report, binding);

		return report;
	}
	
	public Page<Report> getReportsByStatus(ReportStatus status, Pageable pageable){
		return this.reportRepository.getReportsByStatus(status, pageable);
	}
	
	public Page<Report> findReportsByPlayer(final int playerId, Pageable pageable) {
		return this.reportRepository.findReportsByPlayer(playerId, pageable);
	}
	
	public Page<Report> getReportsByStatusAndPlayer(final ReportStatus status, final int playerId, Pageable pageable) {
		return this.reportRepository.getReportsByStatusAndPlayer(status, playerId, pageable);
	}
	
	public void flush(){
		this.reportRepository.flush();
	}
	
	public Page<Report> getMyAnsweredReports(int creatorId, Pageable p){
		return this.reportRepository.getMyAnsweredReports(creatorId, p);
	}

	//Dashboard

	public Double getAvgReportPerUser() {
		return this.reportRepository.getAvgReportPerUser();
	}

	public Double getStddevReportPerUser() {
		return this.reportRepository.getAvgReportPerUser();
	}

	public Double getRatioOfResolvedReport() {
		return this.reportRepository.getRatioOfResolvedReport();
	}

	public Double getRatioOfIrresolvableReport() {
		return this.reportRepository.getRatioOfIrresolvableReport();
	}

	public Double getRatioOfSuspiciousReport() {
		return this.reportRepository.getRatioOfSuspiciousReport();
	}

}
