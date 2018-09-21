
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.ReportUpdate;

public interface ReportUpdateRepository extends JpaRepository<ReportUpdate, Integer> {

	@Query("select ru from ReportUpdate ru where ru IN (select rupd from Report r join r.reportUpdates rupd where r.id=?1)")
	Collection<ReportUpdate> getReportUpdatesByReportId(int reportId);

	@Query("select ru from ReportUpdate ru where ru.isSuspicious = true")
	Collection<ReportUpdate> getSuspiciousReportUpdates();
	
	@Query("select ru from ReportUpdate ru where ru IN (select rupd from Report r join r.reportUpdates rupd where r.id=?1) and ru.isSuspicious = true")
	Collection<ReportUpdate> getSuspiciousReportUpdatesByReportId(int reportId);

	@Query("select ru from ReportUpdate ru where ru IN (select rupd from Report r join r.reportUpdates rupd where r.id=?1) and (ru.administrator.id = ?2 or ru.gameMaster.id = ?2)")
	Collection<ReportUpdate> getReportUpdatesByActorId(int reportId, int actorId);

	@Query("select ru from ReportUpdate ru where ru IN (select rupd from Report r join r.reportUpdates rupd where r.id=?1) and  ru.status = 'RESOLVED'")
	Collection<ReportUpdate> getResolvedReportUpdates(int reportId);

	//Dashboard

	@Query("select avg(1.0*(select count(u) from ReportUpdate u where u.gameMaster!=null and u.gameMaster.id=gm.id)) from GameMaster gm")
	Double avgUpdatesFromGm();

	@Query("select stddev(1.0*(select count(u) from ReportUpdate u where u.gameMaster!=null and u.gameMaster.id=gm.id)) from GameMaster gm")
	Double stddevUpdatesFromGm();

	@Query("select max(1*(select count(u) from ReportUpdate u where u.gameMaster!=null and u.gameMaster.id=gm.id)) from GameMaster gm")
	Integer maxUpdatesFromGm();

	@Query("select min(1*(select count(u) from ReportUpdate u where u.gameMaster!=null and u.gameMaster.id=gm.id)) from GameMaster gm")
	Integer minUpdatesFromGm();

	@Query("select avg(r.reportUpdates.size) from Report r")
	Double avgUpdateFromReport();

	@Query("select stddev(r.reportUpdates.size) from Report r")
	Double stddevUpdateFromReport();

	@Query("select max(r.reportUpdates.size) from Report r")
	Integer maxUpdateFromReport();

	@Query("select min(r.reportUpdates.size) from Report r")
	Integer minUpdateFromReport();

	@Query("select avg(1.0*(select count(u) from ReportUpdate u where u.gameMaster!=null and u.gameMaster.id=gm.id and u.isSuspicious=1)) from GameMaster gm")
	Double avgSuspiciousUpdatesFromGm();

}
