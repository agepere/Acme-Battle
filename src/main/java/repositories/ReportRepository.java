
package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Report;
import domain.ReportStatus;

public interface ReportRepository extends JpaRepository<Report, Integer> {

	@Query("select r from Report r where r.keybladeWielder.id = ?1")
	Collection<Report> findReportsByPlayer(int playerId);
	
	@Query("select r from Report r where r.keybladeWielder.id = ?1")
	Page<Report> findReportsByPlayer(int playerId, Pageable pageable);

	@Query("select r from Report r join r.reportUpdates ru where ru IN (select rupd from ReportUpdate rupd where rupd.id=?1)")
	Report findReportsByReportUpdate(int reportUpdateId);
	
	@Query("select r from Report r join r.reportUpdates ru where ru IN (select ru from ReportUpdate ru where (ru.administrator.id = ?1 or ru.gameMaster.id = ?1))")
	Page<Report> getMyAnsweredReports(int creatorId, Pageable p);
	
	@Query("select r from Report r where r.status = ?1")
	Collection<Report> getReportsByStatus(ReportStatus status);
	
	@Query("select r from Report r where r.status = ?1")
	Page<Report> getReportsByStatus(ReportStatus status, Pageable pageable);

	@Query("select r from Report r where r.status = ?1 and r.keybladeWielder.id = ?2")
	Collection<Report> getReportsByStatusAndPlayer(ReportStatus status, int playerId);
	
	@Query("select r from Report r where r.status = ?1 and r.keybladeWielder.id = ?2")
	Page<Report> getReportsByStatusAndPlayer(ReportStatus status, int playerId, Pageable pageable);

	@Query("select avg(1.0*(select count(r) from Report r where r.keybladeWielder.id=u.id and r.isBug=1)) from KeybladeWielder u")
	Double getAvgReportPerUser();

	@Query("select stddev(1.0*(select count(r) from Report r where r.keybladeWielder.id=u.id and r.isBug=1)) from KeybladeWielder u")
	Double getStddevReportPerUser();

	@Query("select 1.0*(count(r)/(select count(x) from Report x)) from Report r where r.status='RESOLVED'")
	Double getRatioOfResolvedReport();

	@Query("select 1.0*(count(r)/(select count(x) from Report x)) from Report r where r.status='IRRESOLVABLE'")
	Double getRatioOfIrresolvableReport();

	@Query("select 1.0*(count(r)/(select count(x) from Report x)) from Report r where r.status='SUSPICIOUS'")
	Double getRatioOfSuspiciousReport();

}
