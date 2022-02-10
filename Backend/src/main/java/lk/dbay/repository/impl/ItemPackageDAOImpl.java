package lk.dbay.repository.impl;

import lk.dbay.entity.item.ItemPackage;
import lk.dbay.entity.item.ItemPackageItemPackageFeature;
import lk.dbay.entity.item.PackageItemItem;
import lk.dbay.repository.ItemPackageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class ItemPackageDAOImpl implements ItemPackageDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<ItemPackage> getItemPackages(String type, String txt, String category, String district, String town) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ItemPackage> cq = cb.createQuery(ItemPackage.class);
        Root<ItemPackage> itemPackageRoot = cq.from(ItemPackage.class);
        Predicate predicateType = cb.equal(itemPackageRoot.get("itemPackageType"), type);
        Predicate predicateAvailable = cb.equal(itemPackageRoot.get("available"), true);
        Predicate predicateName = cb.like(itemPackageRoot.get("name"), "%" + txt + "%");
        Join<ItemPackage, ItemPackageItemPackageFeature> itemPackageItemPackageFeatures = itemPackageRoot.join("itemPackageItemPackageFeatures");
        Predicate predicateItemPackageFeatures = cb.like(itemPackageItemPackageFeatures.get("itemPackageFeature").get("name"), "%" + txt + "%");
//        Predicate predicateBusinessProfileCategory = null, predicateDistrict = null, predicateTown = null;
        Predicate predicate1 = cb.and(predicateAvailable, predicateType);
        Predicate predicateItemPackage = null;
        List<ItemPackage> items;

        if (type.equals("Item")) {
            predicateItemPackage = cb.or(predicateName, predicateItemPackageFeatures);
        } else if (type.equals("Package")) {
            Join<ItemPackage, PackageItemItem> packageItemItems = itemPackageRoot.join("packageItem").join("packageItemItems");
            Predicate predicatePackageItemItems = cb.like(packageItemItems.get("item").get("itemPackage").get("name"), "%" + txt + "%");
            Join<ItemPackage, ItemPackageItemPackageFeature> packageItemItemsItemFeatures = itemPackageRoot.join("packageItem").join("packageItemItems").join("item").join("itemPackage").join("itemPackageItemPackageFeatures");
            Predicate predicatePackageItemItemsItemFeatures = cb.like(packageItemItemsItemFeatures.get("itemPackageFeature").get("name"), "%" + txt + "%");
            predicateItemPackage = cb.or(predicateName, predicateItemPackageFeatures, predicatePackageItemItems, predicatePackageItemItemsItemFeatures);
        }
        if (!category.equals("0")) {
            Predicate predicateBusinessProfileCategory = cb.like(itemPackageRoot.get("businessProfileCategory").get("businessCategory").get("businessCategoryId"), category);
            predicateItemPackage = cb.and(predicateItemPackage, predicateBusinessProfileCategory);
        }
        if (!district.equals("0")) {
            Predicate predicateDistrict = cb.like(itemPackageRoot.get("businessProfileCategory").get("businessProfile").get("town").get("district").get("districtId"), district);
            predicateItemPackage = cb.and(predicateItemPackage, predicateDistrict);
        }
        if (!town.equals("0")) {
            Predicate predicateTown = cb.like(itemPackageRoot.get("businessProfileCategory").get("businessProfile").get("town").get("townId"), town);
            predicateItemPackage = cb.and(predicateItemPackage, predicateTown);
        }
        predicateItemPackage = cb.and(predicateItemPackage, predicate1);
        CriteriaQuery<ItemPackage> criteriaQueryPackageItem = cq.select(itemPackageRoot).where(predicateItemPackage).distinct(true);
        items = entityManager.createQuery(criteriaQueryPackageItem).getResultList();
        return items;
    }
}
