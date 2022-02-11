package lk.dbay.repository.impl;

import lk.dbay.entity.BusinessProfile;
import lk.dbay.entity.BusinessProfileCategory;
import lk.dbay.entity.item.ItemPackage;
import lk.dbay.repository.ShopDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class ShopDAOImpl implements ShopDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<BusinessProfile> getShops(String txt, String category, String district, String town, String customerId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BusinessProfile> cq = cb.createQuery(BusinessProfile.class);
        Root<BusinessProfile> businessProfileRoot = cq.from(BusinessProfile.class);

        String[] txtsBusinessName = txt.split(" ");
        Predicate predicateNames[] = new Predicate[txtsBusinessName.length];
        for (int i = 0; i < txtsBusinessName.length; i++) {
            predicateNames[i] = cb.like(businessProfileRoot.get("businessName"), "%" + txtsBusinessName[i].trim() + "%");
        }
        Predicate predicateName = cb.or(predicateNames);

        Join<BusinessProfile, BusinessProfileCategory> businessProfileCategories = businessProfileRoot.join("businessProfileCategories");
        Predicate predicateBusinessProfileCategory, predicateShops;
        List<BusinessProfile> businessProfiles;

        if (!category.equals("0")) {
            predicateBusinessProfileCategory = cb.like(businessProfileCategories.get("businessCategory").get("businessCategoryId"), category);
        } else {
            String[] txtsBusinessCat = txt.split(" ");
            Predicate predicateCats[] = new Predicate[txtsBusinessCat.length];
            for (int i = 0; i < txtsBusinessCat.length; i++) {
                predicateCats[i] = cb.like(businessProfileCategories.get("businessCategory").get("name"), "%" + txtsBusinessCat[i].trim() + "%");
            }
            predicateBusinessProfileCategory = cb.or(predicateCats);
        }
        predicateShops = cb.and(predicateName, predicateBusinessProfileCategory);
        if (!district.equals("0")) {
            Predicate predicateDistrict = cb.like(businessProfileRoot.get("town").get("district").get("districtId"), district);
            predicateShops = cb.and(predicateShops, predicateDistrict);
        }
        if (!town.equals("0")) {
            Predicate predicateTown = cb.like(businessProfileRoot.get("town").get("townId"), town);
            predicateShops = cb.and(predicateShops, predicateTown);
        }
        CriteriaQuery<BusinessProfile> criteriaQueryPackageItem = cq.select(businessProfileRoot).where(predicateShops).distinct(true);
        businessProfiles = entityManager.createQuery(criteriaQueryPackageItem).getResultList();
        return businessProfiles;
    }
}
