SELECT `country`.`country_id`,
    `country`.`created_at`,
    `country`.`updated_at`,
    `country`.`name`
FROM `dbay`.`country`;

INSERT INTO `dbay`.`country` (`country_id`, `created_at`, `updated_at`, `name`) VALUES ('C001', '2021-11-19', '2021-11-19', 'Sri Lanka');
INSERT INTO `dbay`.`district` (`district_id`, `created_at`, `updated_at`, `name`, `country_country_id`) VALUES ('D001', '2021-11-19', '2021-11-19', 'Galle', 'C001');
INSERT INTO `dbay`.`district` (`district_id`, `created_at`, `updated_at`, `name`, `country_country_id`) VALUES ('D002', '2021-11-19', '2021-11-19', 'Matara', 'C001');
INSERT INTO `dbay`.`town` (`town_id`, `created_at`, `updated_at`, `name`, `district_district_id`) VALUES ('T001', '2021-11-19', '2021-11-19', 'Akuressa', 'D001');
INSERT INTO `dbay`.`town` (`town_id`, `created_at`, `updated_at`, `name`, `district_district_id`) VALUES ('T002', '2021-11-19', '2021-11-19', 'Karapitiya', 'D001');
INSERT INTO `dbay`.`business_category` (`business_category_id`, `created_at`, `updated_at`, `name`) VALUES ('BC001', '2021-11-19', '2021-11-19', 'Juice Bar');
INSERT INTO `dbay`.`business_category` (`business_category_id`, `created_at`, `updated_at`, `name`) VALUES ('BC002', '2021-11-19', '2021-11-19', 'Cake Baker');
INSERT INTO `dbay`.`item_feature` (`item_feature_id`, `created_at`, `updated_at`, `confirmed`, `name`, `business_category_business_category_id`) VALUES ('IF1', '2020-05-05', '2020-05-05', true, 'White', 'BC001');

SELECT `district`.`district_id`,
    `district`.`created_at`,
    `district`.`updated_at`,
    `district`.`name`,
    `district`.`country_country_id`
FROM `dbay`.`district`;


SELECT `town`.`town_id`,
    `town`.`created_at`,
    `town`.`updated_at`,
    `town`.`name`,
    `town`.`district_district_id`
FROM `dbay`.`town`;


SELECT `business_category`.`business_category_id`,
    `business_category`.`created_at`,
    `business_category`.`updated_at`,
    `business_category`.`name`
FROM `dbay`.`business_category`;


SELECT `business_profile`.`business_pro_id`,
    `business_profile`.`created_at`,
    `business_profile`.`updated_at`,
    `business_profile`.`business_address`,
    `business_profile`.`business_email`,
    `business_profile`.`business_name`,
    `business_profile`.`business_owner`,
    `business_profile`.`business_owner_nic`,
    `business_profile`.`business_registration_code`,
    `business_profile`.`contact_number1`,
    `business_profile`.`contact_number2`,
    `business_profile`.`contact_number3`,
    `business_profile`.`contact_view_count`,
    `business_profile`.`pro_view_count`,
    `business_profile`.`social_fb`,
    `business_profile`.`social_insta`,
    `business_profile`.`social_linked_in`,
    `business_profile`.`social_twitter`,
    `business_profile`.`town_town_id`,
    `business_profile`.`dbay_user_user_id`,
    `business_profile`.`default_business_business_category_id`
FROM `dbay`.`business_profile`;

SELECT `customer_profile`.`customer_pro_id`,
    `customer_profile`.`created_at`,
    `customer_profile`.`updated_at`,
    `customer_profile`.`contact_number1`,
    `customer_profile`.`customer_address`,
    `customer_profile`.`first_name`,
    `customer_profile`.`gender`,
    `customer_profile`.`dbay_user_user_id`,
    `customer_profile`.`town_town_id`
FROM `dbay`.`customer_profile`;


SELECT `dbay_user`.`user_id`,
    `dbay_user`.`created_at`,
    `dbay_user`.`updated_at`,
    `dbay_user`.`available`,
    `dbay_user`.`confirmed`,
    `dbay_user`.`confirmed_at`,
    `dbay_user`.`email`,
    `dbay_user`.`password`,
    `dbay_user`.`role`,
    `dbay_user`.`type`,
    `dbay_user`.`username`,
    `dbay_user`.`verification_code`,
    `dbay_user`.`confirmed_by_user_id`
FROM `dbay`.`dbay_user`;

SELECT `dbay_user_img`.`user_img_id`,
    `dbay_user_img`.`created_at`,
    `dbay_user_img`.`updated_at`,
    `dbay_user_img`.`thumbnail`,
    `dbay_user_img`.`user_img_name`,
    `dbay_user_img`.`user_img_type`,
    `dbay_user_img`.`dbay_user_user_id`
FROM `dbay`.`dbay_user_img`;


SELECT `business_area`.`business_pro_id`,
    `business_area`.`town_id`,
    `business_area`.`created_at`,
    `business_area`.`updated_at`
FROM `dbay`.`business_area`;

SELECT `business_profile_category`.`business_category_id`,
    `business_profile_category`.`business_pro_id`,
    `business_profile_category`.`created_at`,
    `business_profile_category`.`updated_at`
FROM `dbay`.`business_profile_category`;

SELECT `item_feature`.`item_feature_id`,
    `item_feature`.`created_at`,
    `item_feature`.`updated_at`,
    `item_feature`.`confirmed`,
    `item_feature`.`description`,
    `item_feature`.`name`,
    `item_feature`.`business_category_business_category_id`
FROM `dbay`.`item_feature`;


SELECT `item`.`item_id`,
    `item`.`created_at`,
    `item`.`updated_at`,
    `item`.`available`,
    `item`.`description`,
    `item`.`discount`,
    `item`.`discount_type`,
    `item`.`price`,
    `item`.`quantity`,
    `item`.`name`,
    `item`.`business_category_id`,
    `item`.`business_pro_id`,
    `item`.`item_category_item_category_id`,
    `item`.`confirmed`
FROM `dbay`.`item`;


SELECT `item_item_feature`.`item_feature_id`,
    `item_item_feature`.`item_id`,
    `item_item_feature`.`created_at`,
    `item_item_feature`.`updated_at`
FROM `dbay`.`item_item_feature`;

SELECT `item_package_item_package_feature`.`item_package_feature_id`,
    `item_package_item_package_feature`.`item_package_id`,
    `item_package_item_package_feature`.`created_at`,
    `item_package_item_package_feature`.`updated_at`
FROM `dbay`.`item_package_item_package_feature`;


UPDATE `dbay`.`item_feature` SET `confirmed` = true WHERE (`item_feature_id` = 'ITF20211208230630');

SELECT `item_order`.`order_id`,
    `item_order`.`created_at`,
    `item_order`.`updated_at`,
    `item_order`.`order_date`,
    `item_order`.`received_time`,
    `item_order`.`status`,
    `item_order`.`customer_profile_customer_pro_id`
FROM `dbay`.`item_order`;

SELECT `order_detail`.`order_detail_id`,
    `order_detail`.`created_at`,
    `order_detail`.`updated_at`,
    `order_detail`.`discount`,
    `order_detail`.`order_detail_type`,
    `order_detail`.`price`,
    `order_detail`.`quantity`,
    `order_detail`.`status`,
    `order_detail`.`business_category_id`,
    `order_detail`.`business_pro_id`,
    `order_detail`.`item_item_id`,
    `order_detail`.`item_order_order_id`,
    `order_detail`.`item_package_item_package_id`
FROM `dbay`.`order_detail`;


SELECT `item_package`.`item_package_id`,
    `item_package`.`created_at`,
    `item_package`.`updated_at`,
    `item_package`.`confirmed`,
    `item_package`.`description`,
    `item_package`.`discount`,
    `item_package`.`discount_type`,
    `item_package`.`name`,
    `item_package`.`price`,
    `item_package`.`business_category_id`,
    `item_package`.`business_pro_id`,
    `item_package`.`available`,
    `item_package`.`quantity`
FROM `dbay`.`item_package`;

UPDATE `dbay`.`dbay_user` SET `verification_code` = null WHERE (`user_id` = 'U20220120124245');

SELECT `item_category`.`item_category_id`,
    `item_category`.`created_at`,
    `item_category`.`updated_at`,
    `item_category`.`confirmed`,
    `item_category`.`description`,
    `item_category`.`name`,
    `item_category`.`business_category_id`,
    `item_category`.`business_pro_id`
FROM `dbay`.`item_category`;

UPDATE `dbay`.`item` SET `item_category_item_category_id` = null WHERE (`item_id` = 'ITMU2022011702041620220117021435');

select * from order_detail orderdetai0_ 
cross join item item1_ 
cross join item_package itempackag2_ 
cross join item_order itemorder3_ 
where (orderdetai0_.item_item_id=item1_.item_id 
and orderdetai0_.item_package_item_package_id=itempackag2_.item_package_id) 
and orderdetai0_.item_order_order_id=itemorder3_.order_id 
and ((item1_.business_category_id, item1_.business_pro_id)=('BC001', 'B321') or (itempackag2_.business_category_id, itempackag2_.business_pro_id)=('BC001', 'B321')) 
and 
itemorder3_.status='Pending';

select distinct order_detail_id from order_detail orderdetai0_ 
inner join item item1_ 
inner join item_package itempackag2_ 
inner join item_order itemorder3_ 
where (orderdetai0_.item_item_id=item1_.item_id or orderdetai0_.item_item_id is null)
and (orderdetai0_.item_package_item_package_id=itempackag2_.item_package_id or orderdetai0_.item_package_item_package_id is null)
and orderdetai0_.item_order_order_id=itemorder3_.order_id 
and ((item1_.business_category_id, item1_.business_pro_id)=('BC001', 'B321') or (itempackag2_.business_category_id, itempackag2_.business_pro_id)=('BC001', 'B321')) ;

select orderdetai0_.order_detail_id as order_de1_26_0_, item1_.item_id as item_id1_14_1_, itempackag2_.item_package_id as item_pac1_21_2_, itemorder3_.order_id as order_id1_20_3_, orderdetai0_.created_at as created_2_26_0_, orderdetai0_.updated_at as updated_3_26_0_, orderdetai0_.discount as discount4_26_0_, orderdetai0_.item_item_id as item_ite8_26_0_, orderdetai0_.item_order_order_id as item_ord9_26_0_, orderdetai0_.item_package_item_package_id as item_pa10_26_0_, orderdetai0_.order_detail_type as order_de5_26_0_, orderdetai0_.price as price6_26_0_, orderdetai0_.quantity as quantity7_26_0_, item1_.created_at as created_2_14_1_, item1_.updated_at as updated_3_14_1_, item1_.available as availabl4_14_1_, item1_.business_category_id as busines12_14_1_, item1_.business_pro_id as busines13_14_1_, item1_.confirmed as confirme5_14_1_, item1_.description as descript6_14_1_, item1_.discount as discount7_14_1_, item1_.discount_type as discount8_14_1_, item1_.item_category_item_category_id as item_ca14_14_1_, item1_.name as name9_14_1_, item1_.price as price10_14_1_, item1_.quantity as quantit11_14_1_, itempackag2_.created_at as created_2_21_2_, itempackag2_.updated_at as updated_3_21_2_, itempackag2_.available as availabl4_21_2_, itempackag2_.business_category_id as busines12_21_2_, itempackag2_.business_pro_id as busines13_21_2_, itempackag2_.confirmed as confirme5_21_2_, itempackag2_.description as descript6_21_2_, itempackag2_.discount as discount7_21_2_, itempackag2_.discount_type as discount8_21_2_, itempackag2_.name as name9_21_2_, itempackag2_.price as price10_21_2_, itempackag2_.quantity as quantit11_21_2_, itemorder3_.created_at as created_2_20_3_, itemorder3_.updated_at as updated_3_20_3_, itemorder3_.amount as amount4_20_3_, itemorder3_.contact_number as contact_5_20_3_, itemorder3_.customer_profile_customer_pro_id as custome11_20_3_, itemorder3_.discount as discount6_20_3_, itemorder3_.order_date as order_da7_20_3_, itemorder3_.qty as qty8_20_3_, itemorder3_.received_time as received9_20_3_, itemorder3_.status as status10_20_3_ 
from order_detail orderdetai0_ 
inner join item item1_ on (orderdetai0_.item_item_id=item1_.item_id or orderdetai0_.item_item_id is null) 
inner join item_package itempackag2_ on (orderdetai0_.item_package_item_package_id=itempackag2_.item_package_id or orderdetai0_.item_package_item_package_id is null) 
inner join item_order itemorder3_ on (orderdetai0_.item_order_order_id=itemorder3_.order_id) 
where orderdetai0_.item_item_id=item4_.item_id and orderdetai0_.item_package_item_package_id=itempackag5_.item_package_id and orderdetai0_.item_order_order_id=itemorder6_.order_id and ((item4_.business_category_id, item4_.business_pro_id)=('BC001', 'B321') or (itempackag5_.business_category_id, itempackag5_.business_pro_id)=('BC001', 'B321'));