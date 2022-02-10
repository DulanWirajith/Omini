package lk.dbay.repository;

import lk.dbay.entity.BusinessProfile;

import java.util.List;

public interface ShopDAO {

    List<BusinessProfile> getShops(String txt, String category, String district, String town, String customerId);
}
