package lk.dbay.util;

import lk.dbay.entity.Region;
import lk.dbay.repository.RegionR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InitDB {

//    @Autowired
//    private RegionR regionR;
//    @Autowired
//    private SubjectRepository subjectRepository;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
//        if (regionR.findAll().size() == 0) {
//            regionR.save(new Region("R001", "Southern"));
//            privilegeRepository.save(new Privilege("Institute", 2));
//            privilegeRepository.save(new Privilege("Tutors", 3));
//            privilegeRepository.save(new Privilege("Subjects", 4));
//        }
//
//        if (subjectRepository.findAll().size() == 0) {
//            subjectRepository.save(new Subject("S1","Physics","Physics A/L"));
//            subjectRepository.save(new Subject("S2","Applied Maths","Applied Maths A/L"));
//            subjectRepository.save(new Subject("S3","Chemistry","Chemistry A/L"));
//            subjectRepository.save(new Subject("S4","ICT","ICT A/L"));
//            subjectRepository.save(new Subject("S5","Maths","Maths O/L"));
//            subjectRepository.save(new Subject("S6","Sinhala","Sinhala O/L"));
//            subjectRepository.save(new Subject("S7","Science","Science O/L"));
//            subjectRepository.save(new Subject("S8","English","English O/L"));
//        }
//        System.out.println(123);
//        repo.save(new Entity(...));
    }

}
