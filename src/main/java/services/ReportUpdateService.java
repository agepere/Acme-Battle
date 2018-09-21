
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ReportUpdateRepository;
import security.Authority;
import domain.Actor;
import domain.Administrator;
import domain.GameMaster;
import domain.Report;
import domain.ReportStatus;
import domain.ReportUpdate;

@Service
@Transactional
public class ReportUpdateService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ReportUpdateRepository	reportUpdateRepository;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private ReportService			reportService;
	@Autowired
	private Validator				validator;


	// CRUD methods

	public ReportUpdate create() {
		ReportUpdate reportUpdate;

		reportUpdate = new ReportUpdate();

		return reportUpdate;
	}

	public ReportUpdate save(final ReportUpdate reportUpdate, final Integer reportId) {
		Assert.notNull(reportUpdate);
		Report report;
		ReportUpdate saved;
		Collection<ReportUpdate> suspiciousUpdates;

		report = this.reportService.findOne(reportId);
		suspiciousUpdates = this.getSuspiciousReportUpdatesByReportId(reportId);
		
		Assert.isTrue(report.getStatus() != ReportStatus.RESOLVED
				|| (actorService.getPrincipalAuthority().equals("ADMIN") && suspiciousUpdates.size()>0)
				|| (actorService.getPrincipalAuthority().equals("ADMIN") && report.getStatus() != ReportStatus.RESOLVED));

		if (reportUpdate.getId() != 0)
			Assert.isTrue(report.getReportUpdates().contains(reportUpdate), "error.message.distincReport");
		saved = this.reportUpdateRepository.save(reportUpdate);

		if (reportUpdate.getId() == 0)
			report.getReportUpdates().add(saved);

		report.setStatus(saved.getStatus());
		this.reportService.save(report);

		return saved;
	}

	public ReportUpdate findOne(final int reportUpdateId) {
		Assert.notNull(reportUpdateId);

		ReportUpdate ReportUpdate;

		ReportUpdate = this.reportUpdateRepository.findOne(reportUpdateId);

		return ReportUpdate;
	}

	public ReportUpdate findOneToEdit(final int reportUpdateId) {
		Assert.notNull(reportUpdateId);
		Assert.isTrue(this.actorService.getPrincipalAuthority().equals(Authority.ADMIN) || this.actorService.getPrincipalAuthority().equals(Authority.GM));
		Assert.isTrue(this.reportService.findReportsByReportUpdate(reportUpdateId).getStatus() != ReportStatus.RESOLVED, "error.message.noUpdate");

		ReportUpdate reportUpdate;

		reportUpdate = this.reportUpdateRepository.findOne(reportUpdateId);

		Assert.isTrue(reportUpdate.getCreator().equals(this.actorService.findByPrincipal()), "error.message.notOwnUpdate");

		return reportUpdate;
	}

	public Collection<ReportUpdate> findAll() {
		Collection<ReportUpdate> ReportUpdates;

		ReportUpdates = this.reportUpdateRepository.findAll();

		return ReportUpdates;
	}

	public void delete(final ReportUpdate ReportUpdate) {
		Assert.notNull(ReportUpdate);

		this.reportUpdateRepository.delete(ReportUpdate);
	}

	// Other business methods

	public Collection<ReportUpdate> getReportUpdatesByReportId(final int reportId) {
		return this.reportUpdateRepository.getReportUpdatesByReportId(reportId);
	}

	public void markSuspicious(final ReportUpdate reportUpdate) {
		Assert.notNull(reportUpdate);
		Assert.isTrue(actorService.getPrincipalAuthority().equals("PLAYER"));
		
		reportUpdate.setIsSuspicious(true);
		this.reportUpdateRepository.save(reportUpdate);
	}

	public Collection<ReportUpdate> getSuspiciousReportUpdates() {
		Assert.isTrue(this.actorService.getPrincipalAuthority().equals(Authority.ADMIN));

		return this.reportUpdateRepository.getSuspiciousReportUpdates();
	}

	public ReportUpdate reconstruct(final ReportUpdate reportUpdate, final BindingResult binding) {
		Actor actor;

		actor = this.actorService.findByPrincipal();

		if (reportUpdate.getId() == 0) {
			if (this.actorService.getPrincipalAuthority().equals("ADMIN")) {
				reportUpdate.setAdministrator((Administrator) actor);
				reportUpdate.setGameMaster(null);
			} else if (this.actorService.getPrincipalAuthority().equals("GM")) {
				reportUpdate.setGameMaster((GameMaster) actor);
				reportUpdate.setAdministrator(null);
			}

			reportUpdate.setIsSuspicious(false);

		} else {
			final ReportUpdate original = this.findOneToEdit(reportUpdate.getId());

			if (original.getGameMaster() != null) {
				reportUpdate.setGameMaster(original.getGameMaster());
				reportUpdate.setAdministrator(null);
			} else if (original.getAdministrator() != null) {
				reportUpdate.setAdministrator(original.getAdministrator());
				reportUpdate.setGameMaster(null);
			}

			reportUpdate.setIsSuspicious(original.getIsSuspicious());

		}

		reportUpdate.setDate(new Date(System.currentTimeMillis() - 1000));

		this.validator.validate(reportUpdate, binding);

		return reportUpdate;
	}

	public Collection<ReportUpdate> getReportUpdatesByActorId(final int reportId, final int actorId) {
		return this.reportUpdateRepository.getReportUpdatesByActorId(reportId, actorId);
	}

	public Collection<ReportUpdate> getResolvedReportUpdates(final int reportId) {
		return this.reportUpdateRepository.getResolvedReportUpdates(reportId);
	}
	
	public Collection<ReportUpdate> getSuspiciousReportUpdatesByReportId(int reportId){
		return this.reportUpdateRepository.getSuspiciousReportUpdatesByReportId(reportId);
	}
	
	public void flush(){
		this.reportUpdateRepository.flush();
	}

	//dashboard

	public Double avgUpdatesFromGm() {
		return this.reportUpdateRepository.avgUpdatesFromGm();
	}

	public Double stddevUpdatesFromGm() {
		return this.reportUpdateRepository.stddevUpdatesFromGm();
	}

	public Integer maxUpdatesFromGm() {
		return this.reportUpdateRepository.maxUpdatesFromGm();
	}

	public Integer minUpdatesFromGm() {
		return this.reportUpdateRepository.minUpdatesFromGm();
	}

	public Double avgUpdatesFromReport() {
		return this.reportUpdateRepository.avgUpdateFromReport();
	}
	public Double stddevUpdatesFromReport() {
		return this.reportUpdateRepository.stddevUpdateFromReport();
	}
	public Integer maxUpdatesFromReport() {
		return this.reportUpdateRepository.maxUpdateFromReport();
	}
	public Integer minUpdatesFromReport() {
		return this.reportUpdateRepository.minUpdateFromReport();

	}
	public Double avgSuspiciousUpdatesFromGm() {
		return this.reportUpdateRepository.avgSuspiciousUpdatesFromGm();
	}
}
