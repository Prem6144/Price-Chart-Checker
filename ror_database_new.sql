-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 22, 2018 at 10:30 PM
-- Server version: 10.1.25-MariaDB
-- PHP Version: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ror_database_new`
--

-- --------------------------------------------------------

--
-- Table structure for table `account_referrals`
--

CREATE TABLE `account_referrals` (
  `id` int(11) NOT NULL,
  `referral_id` int(11) DEFAULT NULL,
  `referrer_id` int(11) DEFAULT NULL,
  `referral_gift_coins` float DEFAULT NULL,
  `referrer_gift_coins` float DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `account_referral_amounts`
--

CREATE TABLE `account_referral_amounts` (
  `id` int(11) NOT NULL,
  `account_referral_id` int(11) DEFAULT NULL,
  `referral_gift_amount_id` int(11) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `additional_offers`
--

CREATE TABLE `additional_offers` (
  `id` int(11) NOT NULL,
  `deal_id` int(11) DEFAULT NULL,
  `title` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` text COLLATE utf8_unicode_ci,
  `price` float NOT NULL DEFAULT '0',
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `is_nationwide` tinyint(1) DEFAULT '0',
  `is_active` tinyint(1) DEFAULT '1',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `contract_period` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `additional_offers_zipcodes`
--

CREATE TABLE `additional_offers_zipcodes` (
  `additional_offer_id` int(11) NOT NULL,
  `zipcode_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `admins`
--

CREATE TABLE `admins` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `admins`
--

INSERT INTO `admins` (`id`, `name`, `email`, `password`, `created_at`, `updated_at`) VALUES
(1, 'Admin', 'admin321456321@gmail.com', '7c4a8d09ca3762af61e59520943dc26494f8941b', '2018-01-22 00:00:00', '2018-01-22 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `advertisements`
--

CREATE TABLE `advertisements` (
  `id` int(11) NOT NULL,
  `service_category_id` int(11) DEFAULT NULL,
  `service_category_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `image` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` tinyint(1) DEFAULT '1',
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `app_users`
--

CREATE TABLE `app_users` (
  `id` int(11) NOT NULL,
  `user_type` varchar(25) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'residence',
  `business_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `first_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `last_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `encrypted_password` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `address` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `state` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `city` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `zip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `gcm_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `avatar` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `unhashed_password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `device_flag` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `reset_password_token` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `reset_password_sent_at` datetime DEFAULT NULL,
  `remember_created_at` datetime DEFAULT NULL,
  `sign_in_count` int(11) NOT NULL DEFAULT '0',
  `active` tinyint(1) DEFAULT '1',
  `current_sign_in_at` datetime DEFAULT NULL,
  `last_sign_in_at` datetime DEFAULT NULL,
  `current_sign_in_ip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `last_sign_in_ip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `referral_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT '',
  `refer_status` tinyint(1) DEFAULT '0',
  `total_amount` float DEFAULT '0',
  `customer_service_account` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `customer_status` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `credit_worthy` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `customer_contract` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `mobile` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_service_address_same` int(11) DEFAULT NULL,
  `is_shipping_address_same` int(11) DEFAULT NULL,
  `primary_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `secondary_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `primary_id_number` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `secondary_id_number` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email_verification_token` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email_verified` tinyint(1) DEFAULT NULL,
  `password_reset_token` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password_reset_sent_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `app_user_addresses`
--

CREATE TABLE `app_user_addresses` (
  `id` int(11) NOT NULL,
  `app_user_id` int(11) DEFAULT NULL,
  `address_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `zip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address1` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address2` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address_type` int(11) DEFAULT NULL,
  `contact_number` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `state` text COLLATE utf8_unicode_ci,
  `city` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_default` tinyint(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `app_versions`
--

CREATE TABLE `app_versions` (
  `id` int(11) NOT NULL,
  `versn_num` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `device_type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_force_upgrade` tinyint(1) DEFAULT '0',
  `is_normal_upgrade` tinyint(1) DEFAULT '0',
  `app_url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `bandwidth_calculator_settings`
--

CREATE TABLE `bandwidth_calculator_settings` (
  `id` int(11) NOT NULL,
  `email` int(11) DEFAULT NULL,
  `web_page` int(11) DEFAULT NULL,
  `video_calling` int(11) DEFAULT NULL,
  `audio_calling` int(11) DEFAULT NULL,
  `photo_upload_download` int(11) DEFAULT NULL,
  `video_streaming` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `bulk_notifications`
--

CREATE TABLE `bulk_notifications` (
  `id` int(11) NOT NULL,
  `state` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `city` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `zip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `message` text COLLATE utf8_unicode_ci,
  `category` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `bundle_deal_attributes`
--

CREATE TABLE `bundle_deal_attributes` (
  `id` int(11) NOT NULL,
  `deal_id` int(11) DEFAULT NULL,
  `bundle_combo` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `download` float DEFAULT NULL,
  `upload` float DEFAULT NULL,
  `data` float DEFAULT NULL,
  `static_ip` tinyint(1) DEFAULT NULL,
  `domestic_call_minutes` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `international_call_minutes` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `free_channels` int(11) DEFAULT NULL,
  `free_channels_list` text COLLATE utf8_unicode_ci,
  `premium_channels` int(11) DEFAULT NULL,
  `premium_channels_list` text COLLATE utf8_unicode_ci,
  `hd_channels` int(11) DEFAULT NULL,
  `hd_channels_list` text COLLATE utf8_unicode_ci,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `description` text COLLATE utf8_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `bundle_equipments`
--

CREATE TABLE `bundle_equipments` (
  `id` int(11) NOT NULL,
  `bundle_deal_attribute_id` int(11) DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `make` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `price` decimal(30,2) NOT NULL DEFAULT '0.00',
  `installation` text COLLATE utf8_unicode_ci,
  `activation` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `offer` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `deal_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `bundle_service_preferences`
--

CREATE TABLE `bundle_service_preferences` (
  `id` int(11) NOT NULL,
  `service_preference_id` int(11) DEFAULT NULL,
  `upload_speed` float DEFAULT NULL,
  `download_speed` float DEFAULT NULL,
  `data` float DEFAULT NULL,
  `free_channels` int(11) DEFAULT NULL,
  `premium_channels` int(11) DEFAULT NULL,
  `domestic_call_minutes` int(11) DEFAULT NULL,
  `international_call_minutes` int(11) DEFAULT NULL,
  `data_plan` float DEFAULT NULL,
  `data_speed` float DEFAULT NULL,
  `domestic_call_unlimited` tinyint(1) DEFAULT '0',
  `international_call_unlimited` tinyint(1) DEFAULT '0',
  `bundle_combo` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `businesses`
--

CREATE TABLE `businesses` (
  `id` int(11) NOT NULL,
  `business_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `business_type` int(11) DEFAULT NULL,
  `business_status` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `business_dba` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `federal_number` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `db_number` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `ssn` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `contact_number` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `manager_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `manager_contact` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_service_address_same` int(11) DEFAULT NULL,
  `is_shipping_address_same` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `business_addresses`
--

CREATE TABLE `business_addresses` (
  `id` int(11) NOT NULL,
  `business_id` int(11) DEFAULT NULL,
  `address_type` int(11) DEFAULT NULL,
  `address_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `zip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address1` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address2` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `contact_number` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `manager_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `manager_contact` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `state` text COLLATE utf8_unicode_ci,
  `city` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_default` tinyint(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `business_app_users`
--

CREATE TABLE `business_app_users` (
  `id` int(11) NOT NULL,
  `business_id` int(11) DEFAULT NULL,
  `app_user_id` int(11) DEFAULT NULL,
  `role` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `cable_deal_attributes`
--

CREATE TABLE `cable_deal_attributes` (
  `id` int(11) NOT NULL,
  `deal_id` int(11) DEFAULT NULL,
  `free_channels` int(11) DEFAULT NULL,
  `free_channels_list` text COLLATE utf8_unicode_ci,
  `premium_channels` int(11) DEFAULT NULL,
  `premium_channels_list` text COLLATE utf8_unicode_ci,
  `hd_channels` int(11) DEFAULT NULL,
  `hd_channels_list` text COLLATE utf8_unicode_ci,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `channel_ids` text COLLATE utf8_unicode_ci,
  `channel_package_ids` text COLLATE utf8_unicode_ci,
  `channel_count` int(11) DEFAULT NULL,
  `description` text COLLATE utf8_unicode_ci,
  `title` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `amount` decimal(5,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `cable_equipments`
--

CREATE TABLE `cable_equipments` (
  `id` int(11) NOT NULL,
  `cable_deal_attribute_id` int(11) DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `make` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `price` decimal(30,2) NOT NULL DEFAULT '0.00',
  `installation` text COLLATE utf8_unicode_ci,
  `activation` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `offer` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `deal_id` int(11) DEFAULT NULL,
  `description` text COLLATE utf8_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `cable_service_preferences`
--

CREATE TABLE `cable_service_preferences` (
  `id` int(11) NOT NULL,
  `service_preference_id` int(11) DEFAULT NULL,
  `free_channels` int(11) DEFAULT NULL,
  `premium_channels` int(11) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `cashout_infos`
--

CREATE TABLE `cashout_infos` (
  `id` int(11) NOT NULL,
  `app_user_id` int(11) DEFAULT NULL,
  `reedeem_amount` float DEFAULT NULL,
  `email_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_cash` tinyint(1) DEFAULT NULL,
  `gift_card_type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `cellphone_deal_attributes`
--

CREATE TABLE `cellphone_deal_attributes` (
  `id` int(11) NOT NULL,
  `deal_id` int(11) DEFAULT NULL,
  `no_of_lines` int(11) NOT NULL DEFAULT '0',
  `price_per_line` decimal(5,2) NOT NULL DEFAULT '0.00',
  `domestic_call_minutes` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `domestic_text` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `international_call_minutes` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `international_text` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `data_plan` float DEFAULT NULL,
  `data_plan_price` decimal(5,2) NOT NULL DEFAULT '0.00',
  `additional_data` float DEFAULT NULL,
  `additional_data_price` decimal(5,2) NOT NULL DEFAULT '0.00',
  `rollover_data` tinyint(1) DEFAULT '0',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `effective_price` decimal(10,0) DEFAULT '0',
  `description` text COLLATE utf8_unicode_ci,
  `title` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `cellphone_details`
--

CREATE TABLE `cellphone_details` (
  `id` int(11) NOT NULL,
  `cellphone_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `brand` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` text COLLATE utf8_unicode_ci,
  `status` tinyint(1) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `image` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `cellphone_equipments`
--

CREATE TABLE `cellphone_equipments` (
  `id` int(11) NOT NULL,
  `cellphone_deal_attribute_id` int(11) DEFAULT NULL,
  `model` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `make` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `memory` int(11) DEFAULT NULL,
  `price` decimal(30,2) NOT NULL DEFAULT '0.00',
  `installation` text COLLATE utf8_unicode_ci,
  `activation` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `offer` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `deal_id` int(11) DEFAULT NULL,
  `available_colors` text COLLATE utf8_unicode_ci,
  `cellphone_detail_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `cellphone_service_preferences`
--

CREATE TABLE `cellphone_service_preferences` (
  `id` int(11) NOT NULL,
  `service_preference_id` int(11) DEFAULT NULL,
  `domestic_call_minutes` int(11) DEFAULT NULL,
  `international_call_minutes` int(11) DEFAULT NULL,
  `data_plan` float DEFAULT NULL,
  `data_speed` float DEFAULT NULL,
  `domestic_call_unlimited` tinyint(1) DEFAULT '0',
  `international_call_unlimited` tinyint(1) DEFAULT '0',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `no_of_lines` int(11) DEFAULT NULL,
  `cellphone_detail_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `channels`
--

CREATE TABLE `channels` (
  `id` int(11) NOT NULL,
  `category_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `channel_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `channel_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `channel_type` varchar(255) COLLATE utf8_unicode_ci DEFAULT 'normal',
  `description` text COLLATE utf8_unicode_ci,
  `is_hd` tinyint(1) DEFAULT '0',
  `image` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` tinyint(1) DEFAULT '1',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `service_provider_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `channel_packages`
--

CREATE TABLE `channel_packages` (
  `id` int(11) NOT NULL,
  `package_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `package_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `channel_count` int(11) DEFAULT NULL,
  `channel_ids` text COLLATE utf8_unicode_ci,
  `description` text COLLATE utf8_unicode_ci,
  `image` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` tinyint(1) DEFAULT '1',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `price` decimal(5,2) DEFAULT NULL,
  `service_provider_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `checklists`
--

CREATE TABLE `checklists` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `comment_ratings`
--

CREATE TABLE `comment_ratings` (
  `id` int(11) NOT NULL,
  `app_user_id` int(11) DEFAULT NULL,
  `deal_id` int(11) DEFAULT NULL,
  `rating_point` float DEFAULT NULL,
  `status` tinyint(1) DEFAULT '1',
  `comment_title` text COLLATE utf8_unicode_ci,
  `comment_text` text COLLATE utf8_unicode_ci,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `configurables`
--

CREATE TABLE `configurables` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `value` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `deals`
--

CREATE TABLE `deals` (
  `id` int(11) NOT NULL,
  `service_category_id` int(11) DEFAULT NULL,
  `service_provider_id` int(11) DEFAULT NULL,
  `title` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `short_description` text COLLATE utf8_unicode_ci,
  `detail_description` text COLLATE utf8_unicode_ci,
  `price` float NOT NULL DEFAULT '0',
  `is_contract` tinyint(1) NOT NULL DEFAULT '0',
  `contract_period` int(11) NOT NULL DEFAULT '0',
  `url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `image` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `is_nationwide` tinyint(1) DEFAULT '0',
  `deal_type` varchar(100) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'residence',
  `is_active` tinyint(1) DEFAULT '1',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `is_sponsored` tinyint(1) DEFAULT '0',
  `is_customisable` tinyint(1) DEFAULT '0',
  `is_order_available` tinyint(1) DEFAULT '1',
  `effective_price` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `deals_zipcodes`
--

CREATE TABLE `deals_zipcodes` (
  `deal_id` int(11) NOT NULL,
  `zipcode_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `deal_extra_services`
--

CREATE TABLE `deal_extra_services` (
  `id` int(11) NOT NULL,
  `extra_service_id` int(11) DEFAULT NULL,
  `deal_id` int(11) DEFAULT NULL,
  `price` decimal(5,2) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `service_term` int(11) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `deal_include_zipcodes`
--

CREATE TABLE `deal_include_zipcodes` (
  `id` int(11) NOT NULL,
  `deal_id` int(11) NOT NULL,
  `zipcode_id` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `delayed_jobs`
--

CREATE TABLE `delayed_jobs` (
  `id` int(11) NOT NULL,
  `priority` int(11) NOT NULL DEFAULT '0',
  `attempts` int(11) NOT NULL DEFAULT '0',
  `handler` text COLLATE utf8_unicode_ci NOT NULL,
  `last_error` text COLLATE utf8_unicode_ci,
  `run_at` datetime DEFAULT NULL,
  `locked_at` datetime DEFAULT NULL,
  `failed_at` datetime DEFAULT NULL,
  `locked_by` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `queue` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `device_registers`
--

CREATE TABLE `device_registers` (
  `id` int(11) NOT NULL,
  `imei` text COLLATE utf8_unicode_ci,
  `device_id` text COLLATE utf8_unicode_ci,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `token` text COLLATE utf8_unicode_ci,
  `version` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `device_type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `device_trackers`
--

CREATE TABLE `device_trackers` (
  `id` int(11) NOT NULL,
  `device_id` text COLLATE utf8_unicode_ci,
  `service_provider` text COLLATE utf8_unicode_ci,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `imei` text COLLATE utf8_unicode_ci,
  `dual_sim` tinyint(1) DEFAULT NULL,
  `country` text COLLATE utf8_unicode_ci,
  `sim_operator` text COLLATE utf8_unicode_ci,
  `sim_serial_number` text COLLATE utf8_unicode_ci,
  `subscriber_id` text COLLATE utf8_unicode_ci,
  `voice_mail_number` text COLLATE utf8_unicode_ci,
  `location` text COLLATE utf8_unicode_ci,
  `device_type` text COLLATE utf8_unicode_ci,
  `provider_type` text COLLATE utf8_unicode_ci,
  `roaming` tinyint(1) DEFAULT NULL,
  `device_register_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `dynamic_labels`
--

CREATE TABLE `dynamic_labels` (
  `id` int(11) NOT NULL,
  `label_key` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `label_value` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `label_description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `service_provider_id` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `equipment_colors`
--

CREATE TABLE `equipment_colors` (
  `id` int(11) NOT NULL,
  `color_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `color_code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `image` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `extra_services`
--

CREATE TABLE `extra_services` (
  `id` int(11) NOT NULL,
  `service_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `service_category_id` int(11) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `service_description` text COLLATE utf8_unicode_ci,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `gifts`
--

CREATE TABLE `gifts` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `amount` float DEFAULT NULL,
  `activation_count_condition` int(11) DEFAULT '0',
  `is_active` tinyint(1) DEFAULT '1',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `image` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `internet_deal_attributes`
--

CREATE TABLE `internet_deal_attributes` (
  `id` int(11) NOT NULL,
  `deal_id` int(11) DEFAULT NULL,
  `download` float DEFAULT NULL,
  `upload` float DEFAULT NULL,
  `data` float DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `online_storage` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `wifi_hotspot_networks` text COLLATE utf8_unicode_ci,
  `static_ip` tinyint(1) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `description` text COLLATE utf8_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `internet_equipments`
--

CREATE TABLE `internet_equipments` (
  `id` int(11) NOT NULL,
  `internet_deal_attribute_id` int(11) DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `make` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `price` decimal(30,2) NOT NULL DEFAULT '0.00',
  `installation` text COLLATE utf8_unicode_ci,
  `activation` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `offer` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `deal_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `internet_service_preferences`
--

CREATE TABLE `internet_service_preferences` (
  `id` int(11) NOT NULL,
  `service_preference_id` int(11) DEFAULT NULL,
  `upload_speed` float DEFAULT NULL,
  `download_speed` float DEFAULT NULL,
  `online_storage` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `wifi_hotspot` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `leads`
--

CREATE TABLE `leads` (
  `id` int(11) NOT NULL,
  `lead_type` text COLLATE utf8_unicode_ci,
  `service_category_id` int(11) DEFAULT NULL,
  `deal_id` int(11) DEFAULT NULL,
  `lead_name` text COLLATE utf8_unicode_ci,
  `lead_description` text COLLATE utf8_unicode_ci,
  `lead_email` text COLLATE utf8_unicode_ci,
  `lead_contact_number` text COLLATE utf8_unicode_ci,
  `lead_location` text COLLATE utf8_unicode_ci,
  `lead_address` text COLLATE utf8_unicode_ci,
  `lead_spoc_name` text COLLATE utf8_unicode_ci,
  `lead_spoc_email` text COLLATE utf8_unicode_ci,
  `lead_spoc_number` text COLLATE utf8_unicode_ci,
  `lead_spoc_designation` text COLLATE utf8_unicode_ci,
  `lead_response` text COLLATE utf8_unicode_ci,
  `user_id` text COLLATE utf8_unicode_ci,
  `status` text COLLATE utf8_unicode_ci,
  `demo_time` datetime DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `business_name` text COLLATE utf8_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `login_details`
--

CREATE TABLE `login_details` (
  `id` int(11) NOT NULL,
  `partnerable_type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `partnerable_id` int(11) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `news_letters`
--

CREATE TABLE `news_letters` (
  `id` int(11) NOT NULL,
  `sending_date` datetime DEFAULT NULL,
  `subject` text COLLATE utf8_unicode_ci,
  `content` text COLLATE utf8_unicode_ci,
  `user_ids` text COLLATE utf8_unicode_ci,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `attachment_link` text COLLATE utf8_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `notifications`
--

CREATE TABLE `notifications` (
  `id` int(11) NOT NULL,
  `app_user_id` int(11) DEFAULT NULL,
  `recieve_notification` tinyint(1) DEFAULT NULL,
  `day` int(11) DEFAULT NULL,
  `recieve_trending_deals` tinyint(1) DEFAULT '1',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `repeat_notification_frequency` varchar(255) COLLATE utf8_unicode_ci DEFAULT 'Weekly',
  `trending_deal_frequency` varchar(255) COLLATE utf8_unicode_ci DEFAULT 'Weekly',
  `receive_call` tinyint(1) DEFAULT '1',
  `min_service_provider_rating` int(11) DEFAULT NULL,
  `min_deal_rating` int(11) DEFAULT NULL,
  `receive_email` tinyint(1) DEFAULT '1',
  `receive_text` tinyint(1) DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` int(11) NOT NULL,
  `order_id` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `deal_id` int(11) DEFAULT NULL,
  `app_user_id` int(11) DEFAULT NULL,
  `status` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'In-progress',
  `deal_price` float DEFAULT NULL,
  `effective_price` float DEFAULT NULL,
  `activation_date` datetime DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `order_number` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `order_type` int(11) DEFAULT NULL,
  `security_deposit` int(11) DEFAULT NULL,
  `primary_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `secondary_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `primary_id_number` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `secondary_id_number` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `free_text` text COLLATE utf8_unicode_ci,
  `provider_status` varchar(255) COLLATE utf8_unicode_ci DEFAULT 'In-progress',
  `provider_order_number` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `order_addresses`
--

CREATE TABLE `order_addresses` (
  `id` int(11) NOT NULL,
  `order_id` int(11) DEFAULT NULL,
  `address_type` int(11) DEFAULT NULL,
  `address_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `zip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address1` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address2` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `contact_number` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `manager_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `manager_contact` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `state` text COLLATE utf8_unicode_ci,
  `city` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `order_attributes`
--

CREATE TABLE `order_attributes` (
  `id` int(11) NOT NULL,
  `order_id` int(11) DEFAULT NULL,
  `ref_id` int(11) DEFAULT NULL,
  `ref_type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `price` decimal(5,2) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `order_equipments`
--

CREATE TABLE `order_equipments` (
  `id` int(11) NOT NULL,
  `order_id` int(11) DEFAULT NULL,
  `equipment_id` int(11) DEFAULT NULL,
  `equipment_price` decimal(5,2) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `color` text COLLATE utf8_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `order_extra_services`
--

CREATE TABLE `order_extra_services` (
  `id` int(11) NOT NULL,
  `order_id` int(11) DEFAULT NULL,
  `deal_extra_service_id` int(11) DEFAULT NULL,
  `service_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `price` decimal(5,2) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `order_items`
--

CREATE TABLE `order_items` (
  `id` int(11) NOT NULL,
  `order_id` int(11) DEFAULT NULL,
  `ref_number` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `deal_id` int(11) DEFAULT NULL,
  `deal_price` float DEFAULT NULL,
  `effective_price` float DEFAULT NULL,
  `discount_price` float DEFAULT NULL,
  `activation_date` date DEFAULT NULL,
  `status` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `you_save` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `pending_actions`
--

CREATE TABLE `pending_actions` (
  `id` int(11) NOT NULL,
  `action_by` int(11) DEFAULT NULL,
  `pending_with` int(11) DEFAULT NULL,
  `action_type` int(11) DEFAULT NULL,
  `key` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `additional_info` text COLLATE utf8_unicode_ci,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `referral_gift_amounts`
--

CREATE TABLE `referral_gift_amounts` (
  `id` int(11) NOT NULL,
  `referrer_amount` float DEFAULT NULL,
  `referral_amount` float DEFAULT NULL,
  `referrer_gift_image` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `referral_gift_image` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '0',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `referral_infos`
--

CREATE TABLE `referral_infos` (
  `id` int(11) NOT NULL,
  `first_referring_identity` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `referred_user` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `event` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `referring_user_coins` int(11) DEFAULT NULL,
  `referred_user_coins` int(11) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `refer_contact_details`
--

CREATE TABLE `refer_contact_details` (
  `id` int(11) NOT NULL,
  `app_user_id` int(11) DEFAULT NULL,
  `email_id` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `mobile_no` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `rewards`
--

CREATE TABLE `rewards` (
  `id` int(11) NOT NULL,
  `reward_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `reward_value` int(11) DEFAULT NULL,
  `image` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '0',
  `device_platform` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `reward_display_type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `reward_display_on` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `sales_executives`
--

CREATE TABLE `sales_executives` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `role` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `encrypted_password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `reset_password_token` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `remember_created_at` datetime DEFAULT NULL,
  `sign_in_count` int(11) DEFAULT NULL,
  `current_sign_in_at` datetime DEFAULT NULL,
  `last_sign_in_at` datetime DEFAULT NULL,
  `current_sign_in_ip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `last_sign_in_ip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `failed_count` int(11) DEFAULT NULL,
  `password_updated_at` datetime DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `status` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `schema_migrations`
--

CREATE TABLE `schema_migrations` (
  `version` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `sequences`
--

CREATE TABLE `sequences` (
  `id` int(11) NOT NULL,
  `seq_type` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `seq_number` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `service_categories`
--

CREATE TABLE `service_categories` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `description` text COLLATE utf8_unicode_ci,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `service_deal_configs`
--

CREATE TABLE `service_deal_configs` (
  `id` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `config_key` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `config_value` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `service_preferences`
--

CREATE TABLE `service_preferences` (
  `id` int(11) NOT NULL,
  `app_user_id` int(11) DEFAULT NULL,
  `service_category_id` int(11) DEFAULT NULL,
  `service_provider_id` int(11) DEFAULT NULL,
  `service_category_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `service_provider_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `price` float DEFAULT NULL,
  `is_contract` tinyint(1) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `plan_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `service_providers`
--

CREATE TABLE `service_providers` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `service_category_id` int(11) DEFAULT NULL,
  `address` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `state` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `city` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `zip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `telephone` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `logo` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_preferred` tinyint(1) DEFAULT '0',
  `is_active` tinyint(1) DEFAULT '1',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `service_provider_checklists`
--

CREATE TABLE `service_provider_checklists` (
  `id` int(11) NOT NULL,
  `checklist_id` int(11) DEFAULT NULL,
  `service_provider_id` int(11) DEFAULT NULL,
  `service_category_id` int(11) DEFAULT NULL,
  `is_mandatory` tinyint(1) DEFAULT NULL,
  `status` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `statelists`
--

CREATE TABLE `statelists` (
  `id` int(11) NOT NULL,
  `state` text COLLATE utf8_unicode_ci,
  `city` text COLLATE utf8_unicode_ci,
  `county` text COLLATE utf8_unicode_ci,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `subscribe_deals`
--

CREATE TABLE `subscribe_deals` (
  `id` int(11) NOT NULL,
  `app_user_id` int(11) DEFAULT NULL,
  `deal_id` int(11) DEFAULT NULL,
  `active_status` tinyint(1) DEFAULT '0',
  `category_id` int(11) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `telephone_deal_attributes`
--

CREATE TABLE `telephone_deal_attributes` (
  `id` int(11) NOT NULL,
  `deal_id` int(11) DEFAULT NULL,
  `domestic_call_minutes` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  `domestic_receive_minutes` int(11) DEFAULT NULL,
  `domestic_additional_minutes` int(11) DEFAULT NULL,
  `international_call_minutes` varchar(25) COLLATE utf8_unicode_ci DEFAULT NULL,
  `international_landline_minutes` int(11) DEFAULT NULL,
  `international_mobile_minutes` int(11) DEFAULT NULL,
  `international_additional_minutes` int(11) DEFAULT NULL,
  `countries` text COLLATE utf8_unicode_ci,
  `features` text COLLATE utf8_unicode_ci,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `description` text COLLATE utf8_unicode_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `telephone_equipments`
--

CREATE TABLE `telephone_equipments` (
  `id` int(11) NOT NULL,
  `telephone_deal_attribute_id` int(11) DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `make` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `price` decimal(30,2) NOT NULL DEFAULT '0.00',
  `installation` text COLLATE utf8_unicode_ci,
  `activation` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `offer` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `deal_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `telephone_service_preferences`
--

CREATE TABLE `telephone_service_preferences` (
  `id` int(11) NOT NULL,
  `service_preference_id` int(11) DEFAULT NULL,
  `domestic_call_minutes` int(11) DEFAULT NULL,
  `international_call_minutes` int(11) DEFAULT NULL,
  `domestic_call_unlimited` tinyint(1) DEFAULT '0',
  `international_call_unlimited` tinyint(1) DEFAULT '0',
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `trending_deals`
--

CREATE TABLE `trending_deals` (
  `id` int(11) NOT NULL,
  `deal_id` int(11) DEFAULT NULL,
  `subscription_count` int(11) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `role` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `encrypted_password` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `reset_password_token` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `reset_password_sent_at` datetime DEFAULT NULL,
  `remember_created_at` datetime DEFAULT NULL,
  `sign_in_count` int(11) NOT NULL DEFAULT '0',
  `current_sign_in_at` datetime DEFAULT NULL,
  `last_sign_in_at` datetime DEFAULT NULL,
  `current_sign_in_ip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `last_sign_in_ip` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  `failed_count` int(11) DEFAULT NULL,
  `password_updated_at` datetime DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user_gifts`
--

CREATE TABLE `user_gifts` (
  `id` int(11) NOT NULL,
  `app_user_id` int(11) DEFAULT NULL,
  `gift_id` int(11) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  `status` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `zipcodes`
--

CREATE TABLE `zipcodes` (
  `id` int(11) NOT NULL,
  `code` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `area` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `city` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `state` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `country` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account_referrals`
--
ALTER TABLE `account_referrals`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `account_referral_amounts`
--
ALTER TABLE `account_referral_amounts`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `additional_offers`
--
ALTER TABLE `additional_offers`
  ADD PRIMARY KEY (`id`),
  ADD KEY `index_additional_offers_on_deal_id` (`deal_id`);

--
-- Indexes for table `admins`
--
ALTER TABLE `admins`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `advertisements`
--
ALTER TABLE `advertisements`
  ADD PRIMARY KEY (`id`),
  ADD KEY `index_advertisements_on_service_category_id` (`service_category_id`);

--
-- Indexes for table `app_users`
--
ALTER TABLE `app_users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `index_app_users_on_email` (`email`),
  ADD UNIQUE KEY `index_app_users_on_reset_password_token` (`reset_password_token`);

--
-- Indexes for table `app_user_addresses`
--
ALTER TABLE `app_user_addresses`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `app_versions`
--
ALTER TABLE `app_versions`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `bandwidth_calculator_settings`
--
ALTER TABLE `bandwidth_calculator_settings`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `bulk_notifications`
--
ALTER TABLE `bulk_notifications`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `bundle_deal_attributes`
--
ALTER TABLE `bundle_deal_attributes`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `bundle_equipments`
--
ALTER TABLE `bundle_equipments`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `bundle_service_preferences`
--
ALTER TABLE `bundle_service_preferences`
  ADD PRIMARY KEY (`id`),
  ADD KEY `index_bundle_service_preferences_on_service_preference_id` (`service_preference_id`);

--
-- Indexes for table `businesses`
--
ALTER TABLE `businesses`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `business_addresses`
--
ALTER TABLE `business_addresses`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `business_app_users`
--
ALTER TABLE `business_app_users`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cable_deal_attributes`
--
ALTER TABLE `cable_deal_attributes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `index_cable_deal_attributes_on_deal_id` (`deal_id`);

--
-- Indexes for table `cable_equipments`
--
ALTER TABLE `cable_equipments`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cable_service_preferences`
--
ALTER TABLE `cable_service_preferences`
  ADD PRIMARY KEY (`id`),
  ADD KEY `index_cable_service_preferences_on_service_preference_id` (`service_preference_id`);

--
-- Indexes for table `cashout_infos`
--
ALTER TABLE `cashout_infos`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cellphone_deal_attributes`
--
ALTER TABLE `cellphone_deal_attributes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `index_cellphone_deal_attributes_on_deal_id` (`deal_id`);

--
-- Indexes for table `cellphone_details`
--
ALTER TABLE `cellphone_details`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cellphone_equipments`
--
ALTER TABLE `cellphone_equipments`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `cellphone_service_preferences`
--
ALTER TABLE `cellphone_service_preferences`
  ADD PRIMARY KEY (`id`),
  ADD KEY `index_cellphone_service_preferences_on_service_preference_id` (`service_preference_id`);

--
-- Indexes for table `channels`
--
ALTER TABLE `channels`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `channel_packages`
--
ALTER TABLE `channel_packages`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `checklists`
--
ALTER TABLE `checklists`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `comment_ratings`
--
ALTER TABLE `comment_ratings`
  ADD PRIMARY KEY (`id`),
  ADD KEY `index_comment_ratings_on_app_user_id` (`app_user_id`),
  ADD KEY `index_comment_ratings_on_deal_id` (`deal_id`);

--
-- Indexes for table `configurables`
--
ALTER TABLE `configurables`
  ADD PRIMARY KEY (`id`),
  ADD KEY `index_configurables_on_name` (`name`);

--
-- Indexes for table `deals`
--
ALTER TABLE `deals`
  ADD PRIMARY KEY (`id`),
  ADD KEY `index_deals_on_service_category_id` (`service_category_id`),
  ADD KEY `index_deals_on_service_provider_id` (`service_provider_id`);

--
-- Indexes for table `deal_extra_services`
--
ALTER TABLE `deal_extra_services`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `deal_include_zipcodes`
--
ALTER TABLE `deal_include_zipcodes`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `delayed_jobs`
--
ALTER TABLE `delayed_jobs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `delayed_jobs_priority` (`priority`,`run_at`);

--
-- Indexes for table `device_registers`
--
ALTER TABLE `device_registers`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `device_trackers`
--
ALTER TABLE `device_trackers`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `dynamic_labels`
--
ALTER TABLE `dynamic_labels`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `equipment_colors`
--
ALTER TABLE `equipment_colors`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `extra_services`
--
ALTER TABLE `extra_services`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `gifts`
--
ALTER TABLE `gifts`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `internet_deal_attributes`
--
ALTER TABLE `internet_deal_attributes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `index_internet_deal_attributes_on_deal_id` (`deal_id`);

--
-- Indexes for table `internet_equipments`
--
ALTER TABLE `internet_equipments`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `internet_service_preferences`
--
ALTER TABLE `internet_service_preferences`
  ADD PRIMARY KEY (`id`),
  ADD KEY `index_internet_service_preferences_on_service_preference_id` (`service_preference_id`);

--
-- Indexes for table `leads`
--
ALTER TABLE `leads`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `login_details`
--
ALTER TABLE `login_details`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `news_letters`
--
ALTER TABLE `news_letters`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`id`),
  ADD KEY `index_notifications_on_app_user_id` (`app_user_id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `order_addresses`
--
ALTER TABLE `order_addresses`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `order_attributes`
--
ALTER TABLE `order_attributes`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `order_equipments`
--
ALTER TABLE `order_equipments`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `order_extra_services`
--
ALTER TABLE `order_extra_services`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `order_items`
--
ALTER TABLE `order_items`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pending_actions`
--
ALTER TABLE `pending_actions`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `referral_gift_amounts`
--
ALTER TABLE `referral_gift_amounts`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `referral_infos`
--
ALTER TABLE `referral_infos`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `refer_contact_details`
--
ALTER TABLE `refer_contact_details`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `rewards`
--
ALTER TABLE `rewards`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sales_executives`
--
ALTER TABLE `sales_executives`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `schema_migrations`
--
ALTER TABLE `schema_migrations`
  ADD UNIQUE KEY `unique_schema_migrations` (`version`);

--
-- Indexes for table `sequences`
--
ALTER TABLE `sequences`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `service_categories`
--
ALTER TABLE `service_categories`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `service_deal_configs`
--
ALTER TABLE `service_deal_configs`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `service_preferences`
--
ALTER TABLE `service_preferences`
  ADD PRIMARY KEY (`id`),
  ADD KEY `index_service_preferences_on_app_user_id` (`app_user_id`),
  ADD KEY `index_service_preferences_on_service_category_id` (`service_category_id`),
  ADD KEY `index_service_preferences_on_service_provider_id` (`service_provider_id`);

--
-- Indexes for table `service_providers`
--
ALTER TABLE `service_providers`
  ADD PRIMARY KEY (`id`),
  ADD KEY `index_service_providers_on_service_category_id` (`service_category_id`);

--
-- Indexes for table `service_provider_checklists`
--
ALTER TABLE `service_provider_checklists`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `statelists`
--
ALTER TABLE `statelists`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `subscribe_deals`
--
ALTER TABLE `subscribe_deals`
  ADD PRIMARY KEY (`id`),
  ADD KEY `index_subscribe_deals_on_app_user_id` (`app_user_id`),
  ADD KEY `index_subscribe_deals_on_deal_id` (`deal_id`);

--
-- Indexes for table `telephone_deal_attributes`
--
ALTER TABLE `telephone_deal_attributes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `index_telephone_deal_attributes_on_deal_id` (`deal_id`);

--
-- Indexes for table `telephone_equipments`
--
ALTER TABLE `telephone_equipments`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `telephone_service_preferences`
--
ALTER TABLE `telephone_service_preferences`
  ADD PRIMARY KEY (`id`),
  ADD KEY `index_telephone_service_preferences_on_service_preference_id` (`service_preference_id`);

--
-- Indexes for table `trending_deals`
--
ALTER TABLE `trending_deals`
  ADD PRIMARY KEY (`id`),
  ADD KEY `index_trending_deals_on_deal_id` (`deal_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `index_users_on_email` (`email`),
  ADD UNIQUE KEY `index_users_on_reset_password_token` (`reset_password_token`);

--
-- Indexes for table `user_gifts`
--
ALTER TABLE `user_gifts`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `zipcodes`
--
ALTER TABLE `zipcodes`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account_referrals`
--
ALTER TABLE `account_referrals`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `account_referral_amounts`
--
ALTER TABLE `account_referral_amounts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `additional_offers`
--
ALTER TABLE `additional_offers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=63;
--
-- AUTO_INCREMENT for table `admins`
--
ALTER TABLE `admins`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `advertisements`
--
ALTER TABLE `advertisements`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `app_users`
--
ALTER TABLE `app_users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1731;
--
-- AUTO_INCREMENT for table `app_user_addresses`
--
ALTER TABLE `app_user_addresses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=742;
--
-- AUTO_INCREMENT for table `app_versions`
--
ALTER TABLE `app_versions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `bandwidth_calculator_settings`
--
ALTER TABLE `bandwidth_calculator_settings`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `bulk_notifications`
--
ALTER TABLE `bulk_notifications`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `bundle_deal_attributes`
--
ALTER TABLE `bundle_deal_attributes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;
--
-- AUTO_INCREMENT for table `bundle_equipments`
--
ALTER TABLE `bundle_equipments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;
--
-- AUTO_INCREMENT for table `bundle_service_preferences`
--
ALTER TABLE `bundle_service_preferences`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=419;
--
-- AUTO_INCREMENT for table `businesses`
--
ALTER TABLE `businesses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=473;
--
-- AUTO_INCREMENT for table `business_addresses`
--
ALTER TABLE `business_addresses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=651;
--
-- AUTO_INCREMENT for table `business_app_users`
--
ALTER TABLE `business_app_users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=580;
--
-- AUTO_INCREMENT for table `cable_deal_attributes`
--
ALTER TABLE `cable_deal_attributes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;
--
-- AUTO_INCREMENT for table `cable_equipments`
--
ALTER TABLE `cable_equipments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;
--
-- AUTO_INCREMENT for table `cable_service_preferences`
--
ALTER TABLE `cable_service_preferences`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=378;
--
-- AUTO_INCREMENT for table `cashout_infos`
--
ALTER TABLE `cashout_infos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `cellphone_deal_attributes`
--
ALTER TABLE `cellphone_deal_attributes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=104;
--
-- AUTO_INCREMENT for table `cellphone_details`
--
ALTER TABLE `cellphone_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `cellphone_equipments`
--
ALTER TABLE `cellphone_equipments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=60;
--
-- AUTO_INCREMENT for table `cellphone_service_preferences`
--
ALTER TABLE `cellphone_service_preferences`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=448;
--
-- AUTO_INCREMENT for table `channels`
--
ALTER TABLE `channels`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=134;
--
-- AUTO_INCREMENT for table `channel_packages`
--
ALTER TABLE `channel_packages`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `checklists`
--
ALTER TABLE `checklists`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `comment_ratings`
--
ALTER TABLE `comment_ratings`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;
--
-- AUTO_INCREMENT for table `configurables`
--
ALTER TABLE `configurables`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `deals`
--
ALTER TABLE `deals`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=230;
--
-- AUTO_INCREMENT for table `deal_extra_services`
--
ALTER TABLE `deal_extra_services`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
--
-- AUTO_INCREMENT for table `deal_include_zipcodes`
--
ALTER TABLE `deal_include_zipcodes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `delayed_jobs`
--
ALTER TABLE `delayed_jobs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3159;
--
-- AUTO_INCREMENT for table `device_registers`
--
ALTER TABLE `device_registers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3930;
--
-- AUTO_INCREMENT for table `device_trackers`
--
ALTER TABLE `device_trackers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9339;
--
-- AUTO_INCREMENT for table `dynamic_labels`
--
ALTER TABLE `dynamic_labels`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `equipment_colors`
--
ALTER TABLE `equipment_colors`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT for table `extra_services`
--
ALTER TABLE `extra_services`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT for table `gifts`
--
ALTER TABLE `gifts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `internet_deal_attributes`
--
ALTER TABLE `internet_deal_attributes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=66;
--
-- AUTO_INCREMENT for table `internet_equipments`
--
ALTER TABLE `internet_equipments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;
--
-- AUTO_INCREMENT for table `internet_service_preferences`
--
ALTER TABLE `internet_service_preferences`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=798;
--
-- AUTO_INCREMENT for table `leads`
--
ALTER TABLE `leads`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `login_details`
--
ALTER TABLE `login_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=124;
--
-- AUTO_INCREMENT for table `news_letters`
--
ALTER TABLE `news_letters`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `notifications`
--
ALTER TABLE `notifications`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1267;
--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2166;
--
-- AUTO_INCREMENT for table `order_addresses`
--
ALTER TABLE `order_addresses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4193;
--
-- AUTO_INCREMENT for table `order_attributes`
--
ALTER TABLE `order_attributes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=408;
--
-- AUTO_INCREMENT for table `order_equipments`
--
ALTER TABLE `order_equipments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=417;
--
-- AUTO_INCREMENT for table `order_extra_services`
--
ALTER TABLE `order_extra_services`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=221;
--
-- AUTO_INCREMENT for table `order_items`
--
ALTER TABLE `order_items`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1777;
--
-- AUTO_INCREMENT for table `pending_actions`
--
ALTER TABLE `pending_actions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `referral_gift_amounts`
--
ALTER TABLE `referral_gift_amounts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `referral_infos`
--
ALTER TABLE `referral_infos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `refer_contact_details`
--
ALTER TABLE `refer_contact_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6065;
--
-- AUTO_INCREMENT for table `rewards`
--
ALTER TABLE `rewards`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `sales_executives`
--
ALTER TABLE `sales_executives`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `sequences`
--
ALTER TABLE `sequences`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `service_categories`
--
ALTER TABLE `service_categories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `service_deal_configs`
--
ALTER TABLE `service_deal_configs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT for table `service_preferences`
--
ALTER TABLE `service_preferences`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2519;
--
-- AUTO_INCREMENT for table `service_providers`
--
ALTER TABLE `service_providers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=119;
--
-- AUTO_INCREMENT for table `service_provider_checklists`
--
ALTER TABLE `service_provider_checklists`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `statelists`
--
ALTER TABLE `statelists`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5409;
--
-- AUTO_INCREMENT for table `subscribe_deals`
--
ALTER TABLE `subscribe_deals`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `telephone_deal_attributes`
--
ALTER TABLE `telephone_deal_attributes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
--
-- AUTO_INCREMENT for table `telephone_equipments`
--
ALTER TABLE `telephone_equipments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `telephone_service_preferences`
--
ALTER TABLE `telephone_service_preferences`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=467;
--
-- AUTO_INCREMENT for table `trending_deals`
--
ALTER TABLE `trending_deals`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `user_gifts`
--
ALTER TABLE `user_gifts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=100;
--
-- AUTO_INCREMENT for table `zipcodes`
--
ALTER TABLE `zipcodes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43665;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `additional_offers`
--
ALTER TABLE `additional_offers`
  ADD CONSTRAINT `fk_rails_453053f36f` FOREIGN KEY (`deal_id`) REFERENCES `deals` (`id`);

--
-- Constraints for table `advertisements`
--
ALTER TABLE `advertisements`
  ADD CONSTRAINT `fk_rails_6ce1295d95` FOREIGN KEY (`service_category_id`) REFERENCES `service_categories` (`id`);

--
-- Constraints for table `bundle_service_preferences`
--
ALTER TABLE `bundle_service_preferences`
  ADD CONSTRAINT `fk_rails_2794b7128c` FOREIGN KEY (`service_preference_id`) REFERENCES `service_preferences` (`id`);

--
-- Constraints for table `cable_service_preferences`
--
ALTER TABLE `cable_service_preferences`
  ADD CONSTRAINT `fk_rails_6506edd22b` FOREIGN KEY (`service_preference_id`) REFERENCES `service_preferences` (`id`);

--
-- Constraints for table `cellphone_service_preferences`
--
ALTER TABLE `cellphone_service_preferences`
  ADD CONSTRAINT `fk_rails_7c51c3e7ed` FOREIGN KEY (`service_preference_id`) REFERENCES `service_preferences` (`id`);

--
-- Constraints for table `comment_ratings`
--
ALTER TABLE `comment_ratings`
  ADD CONSTRAINT `fk_rails_25bdd9844a` FOREIGN KEY (`deal_id`) REFERENCES `deals` (`id`),
  ADD CONSTRAINT `fk_rails_34b5f08772` FOREIGN KEY (`app_user_id`) REFERENCES `app_users` (`id`);

--
-- Constraints for table `deals`
--
ALTER TABLE `deals`
  ADD CONSTRAINT `fk_rails_d48fe81581` FOREIGN KEY (`service_category_id`) REFERENCES `service_categories` (`id`);

--
-- Constraints for table `internet_deal_attributes`
--
ALTER TABLE `internet_deal_attributes`
  ADD CONSTRAINT `fk_rails_3b3b6dc165` FOREIGN KEY (`deal_id`) REFERENCES `deals` (`id`);

--
-- Constraints for table `internet_service_preferences`
--
ALTER TABLE `internet_service_preferences`
  ADD CONSTRAINT `fk_rails_88f5c8dff1` FOREIGN KEY (`service_preference_id`) REFERENCES `service_preferences` (`id`);

--
-- Constraints for table `notifications`
--
ALTER TABLE `notifications`
  ADD CONSTRAINT `fk_rails_7922ee9b40` FOREIGN KEY (`app_user_id`) REFERENCES `app_users` (`id`);

--
-- Constraints for table `service_preferences`
--
ALTER TABLE `service_preferences`
  ADD CONSTRAINT `fk_rails_0dca496e33` FOREIGN KEY (`app_user_id`) REFERENCES `app_users` (`id`),
  ADD CONSTRAINT `fk_rails_d3e31cba09` FOREIGN KEY (`service_category_id`) REFERENCES `service_categories` (`id`);

--
-- Constraints for table `service_providers`
--
ALTER TABLE `service_providers`
  ADD CONSTRAINT `fk_rails_d7c97a00a6` FOREIGN KEY (`service_category_id`) REFERENCES `service_categories` (`id`);

--
-- Constraints for table `subscribe_deals`
--
ALTER TABLE `subscribe_deals`
  ADD CONSTRAINT `fk_rails_5c3a6131a4` FOREIGN KEY (`deal_id`) REFERENCES `deals` (`id`),
  ADD CONSTRAINT `fk_rails_f80552f937` FOREIGN KEY (`app_user_id`) REFERENCES `app_users` (`id`);

--
-- Constraints for table `telephone_deal_attributes`
--
ALTER TABLE `telephone_deal_attributes`
  ADD CONSTRAINT `fk_rails_a4025f5e48` FOREIGN KEY (`deal_id`) REFERENCES `deals` (`id`);

--
-- Constraints for table `telephone_service_preferences`
--
ALTER TABLE `telephone_service_preferences`
  ADD CONSTRAINT `fk_rails_f88b15afd5` FOREIGN KEY (`service_preference_id`) REFERENCES `service_preferences` (`id`);

--
-- Constraints for table `trending_deals`
--
ALTER TABLE `trending_deals`
  ADD CONSTRAINT `fk_rails_bab2438672` FOREIGN KEY (`deal_id`) REFERENCES `deals` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
