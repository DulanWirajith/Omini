SELECT `country`.`country_id`,
    `country`.`created_at`,
    `country`.`updated_at`,
    `country`.`name`
FROM `dbay`.`country`;

INSERT INTO `dbay`.`country` (`country_id`, `created_at`, `updated_at`, `name`) VALUES ('C001', '2021-11-19', '2021-11-19', 'Sri Lanka');

SELECT `district`.`district_id`,
    `district`.`created_at`,
    `district`.`updated_at`,
    `district`.`name`,
    `district`.`country_country_id`
FROM `dbay`.`district`;

INSERT INTO `dbay`.`district` (`district_id`, `created_at`, `updated_at`, `name`, `country_country_id`) VALUES ('D001', '2021-11-19', '2021-11-19', 'Galle', 'C001');
INSERT INTO `dbay`.`district` (`district_id`, `created_at`, `updated_at`, `name`, `country_country_id`) VALUES ('D002', '2021-11-19', '2021-11-19', 'Matara', 'C001');

SELECT `town`.`town_id`,
    `town`.`created_at`,
    `town`.`updated_at`,
    `town`.`name`,
    `town`.`district_district_id`
FROM `dbay`.`town`;

INSERT INTO `dbay`.`town` (`town_id`, `created_at`, `updated_at`, `name`, `district_district_id`) VALUES ('T001', '2021-11-19', '2021-11-19', 'Akuressa', 'D001');
INSERT INTO `dbay`.`town` (`town_id`, `created_at`, `updated_at`, `name`, `district_district_id`) VALUES ('T002', '2021-11-19', '2021-11-19', 'Karapitiya', 'D001');

SELECT `business_category`.`business_category_id`,
    `business_category`.`created_at`,
    `business_category`.`updated_at`,
    `business_category`.`name`
FROM `dbay`.`business_category`;

INSERT INTO `dbay`.`business_category` (`business_category_id`, `created_at`, `updated_at`, `name`) VALUES ('BC001', '2021-11-19', '2021-11-19', 'Juice Bar');
INSERT INTO `dbay`.`business_category` (`business_category_id`, `created_at`, `updated_at`, `name`) VALUES ('BC002', '2021-11-19', '2021-11-19', 'Cake Baker');

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
    `business_profile`.`user_user_id`
FROM `dbay`.`business_profile`;


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

INSERT INTO `dbay`.`item_feature` (`item_feature_id`, `created_at`, `updated_at`, `confirmed`, `name`, `business_category_business_category_id`) VALUES ('IF1', '2020-05-05', '2020-05-05', true, 'White', 'BC001');

SELECT `item`.`item_id`,
    `item`.`created_at`,
    `item`.`updated_at`,
    `item`.`item_description`,
    `item`.`item_discount`,
    `item`.`item_discount_type`,
    `item`.`item_img`,
    `item`.`item_img_name`,
    `item`.`item_img_type`,
    `item`.`item_price`,
    `item`.`item_qty`,
    `item`.`item_title`,
    `item`.`business_category_id`,
    `item`.`business_pro_id`,
    `item`.`item_category_item_category_id`
FROM `dbay`.`item`;

SELECT `item_item_feature`.`item_feature_id`,
    `item_item_feature`.`item_id`,
    `item_item_feature`.`created_at`,
    `item_item_feature`.`updated_at`
FROM `dbay`.`item_item_feature`;

UPDATE `dbay`.`item_feature` SET `confirmed` = true WHERE (`item_feature_id` = 'ITF20211208230630');

SELECT `item_order`.`order_id`,
    `item_order`.`created_at`,
    `item_order`.`updated_at`,
    `item_order`.`amount`,
    `item_order`.`contact_number`,
    `item_order`.`discount`,
    `item_order`.`order_date`,
    `item_order`.`received_time`,
    `item_order`.`status`,
    `item_order`.`customer_profile_customer_pro_id`,
    `item_order`.`qty`
FROM `dbay`.`item_order`;


SELECT `order_detail`.`order_detail_id`,
    `order_detail`.`created_at`,
    `order_detail`.`updated_at`,
    `order_detail`.`discount`,
    `order_detail`.`price`,
    `order_detail`.`quantity`,
    `order_detail`.`item_item_id`,
    `order_detail`.`item_order_order_id`,
    `order_detail`.`item_package_item_package_id`,
    `order_detail`.`order_detail_type`
FROM `dbay`.`order_detail`;
