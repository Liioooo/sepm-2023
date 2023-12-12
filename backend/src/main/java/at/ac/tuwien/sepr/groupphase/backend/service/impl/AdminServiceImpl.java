package at.ac.tuwien.sepr.groupphase.backend.service.impl;

import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.EventSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.NewsSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.endpoint.dto.UserSearchDto;
import at.ac.tuwien.sepr.groupphase.backend.entity.ApplicationUser;
import at.ac.tuwien.sepr.groupphase.backend.entity.Event;
import at.ac.tuwien.sepr.groupphase.backend.entity.News;
import at.ac.tuwien.sepr.groupphase.backend.repository.AdminRepository;
import at.ac.tuwien.sepr.groupphase.backend.service.AdminService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Page<ApplicationUser> getUsersBySearch(UserSearchDto search, Pageable pageable) {
        return this.adminRepository.findUserBySearchCriteria(search, pageable);
    }

    @Override
    public Page<Event> getEventsBySearch(EventSearchDto search, Pageable pageable) {
        return this.adminRepository.findEventBySearchCriteria(search, pageable);
    }

    @Override
    public Page<News> getNewsBySearch(NewsSearchDto search, Pageable pageable) {
        return this.adminRepository.findNewsBySearchCriteria(search, pageable);
    }
}
