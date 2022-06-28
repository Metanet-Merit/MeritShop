package com.merit.meritShop.user.service;

import com.merit.meritShop.board.domain.Qna;
import com.merit.meritShop.common.domain.Result;
import com.merit.meritShop.common.domain.ResultCode;
import com.merit.meritShop.item.domain.Item;
import com.merit.meritShop.item.repository.ItemRepository;
import com.merit.meritShop.order.domain.OrderItem;
import com.merit.meritShop.order.repository.OrderItemRepository;
import com.merit.meritShop.user.domain.Review;
import com.merit.meritShop.user.domain.User;
import com.merit.meritShop.user.dto.ReviewFormDTO;
import com.merit.meritShop.user.repository.ReviewRepository;
import com.merit.meritShop.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class ReviewService {


    ReviewRepository reviewRepository;

    ItemRepository itemRepository;

    UserRepository userRepository;

    OrderItemRepository orderItemRepository;

    @Autowired
    public ReviewService(
            ReviewRepository reviewRepository,
            ItemRepository itemRepository,
            UserRepository userRepository,
            OrderItemRepository orderItemRepository
    ) {
        this.reviewRepository = reviewRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public Result<Page<ReviewFormDTO>> getReviewPages(Long userId, Pageable pageable) {


        try {

            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isEmpty()) {
                return ResultCode.USER_NOT_EXISTS.result();
            }
            User user = optionalUser.get();
            Page<Review> reviewList = reviewRepository.findReviewByUser(user, pageable);

            Page<ReviewFormDTO> map = reviewList.map(review -> ReviewFormDTO.builder()
                    .content(review.getContent())
                    .rate(review.getRate())
                    .reviewDate(review.getCreatedDate())
                    .orderDate(review.getOrderItem().getOrders().getOrderDate())
                    .orderItemName(review.getOrderItem().getItem().getItemName())
                    .userName(user.getUserName())
                    .uuidName(review.getOrderItem().getItem().getImgUrl())
                    .category(review.getOrderItem().getItem().getCategory())
                    .build());

            return ResultCode.Success.result(map);
        } catch (Exception e) {
            return ResultCode.DB_ERROR.result();
        }

    }

    public Result<Page<ReviewFormDTO>> getReviews(Long userId, Pageable pageable) {

        try {

            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isEmpty()) {
                return ResultCode.USER_NOT_EXISTS.result();
            }
            User user = optionalUser.get();
            Page<Review> reviewList = reviewRepository.findReviewByUser(user, pageable);

            Page<ReviewFormDTO> map = reviewList.map(review -> ReviewFormDTO.builder()
                    .content(review.getContent())
                    .rate(review.getRate())
                    .reviewDate(review.getCreatedDate())
                    .orderDate(review.getOrderItem().getOrders().getOrderDate())
                    .orderItemName(review.getOrderItem().getItem().getItemName())
                    .userName(user.getUserName())
                    .uuidName(review.getOrderItem().getItem().getImgUrl())
                    .review_uuidName(review.getUuidName())
                    .category(review.getOrderItem().getItem().getCategory())
                    .reviewId(review.getReviewId())
                    .orderItemId(review.getOrderItem().getOrderItemId())
                    .build());

            return ResultCode.Success.result(map);
        } catch (Exception e) {
            return ResultCode.DB_ERROR.result();
        }

    }

    public Result<Map<String, Object>> getItemReviews(Long itemId, Pageable pageable) {

        try {
            Map<String, Object> map = new HashMap<>();
            Item item = itemRepository.findById(itemId).get();
            List<OrderItem> orderItemList = orderItemRepository.findOrderItemByItem(item);
            List<ReviewFormDTO> reviewFormDTOList = new ArrayList<>();

            for (OrderItem orderItem : orderItemList) {
                List<Review> reviewList = reviewRepository.findReviewByOrderItem(orderItem);
                for (Review review : reviewList) {
                    if (review != null) {

                        ReviewFormDTO reviewFormDTO = ReviewFormDTO.builder()
                                .content(review.getContent())
                                .rate(review.getRate())
                                .reviewDate(review.getCreatedDate())
                                .orderDate(orderItem.getOrders().getOrderDate())
                                .orderItemName(item.getItemName())
                                .userName(review.getUser().getUserName())
                                .category(item.getCategory())
                                .uuidName(item.getImgUrl())
                                .build();

                        reviewFormDTOList.add(reviewFormDTO);
                    }
                }
            }

            map.put("reviews", reviewFormDTOList);
            map.put("count", reviewFormDTOList.size());

            return ResultCode.Success.result(map);
        } catch (Exception e) {
            return ResultCode.DB_ERROR.result();
        }

    }

    public Result<Map<String, Object>> getItemReviews(Long itemId) {

        try {
            Map<String, Object> map = new HashMap<>();
            Item item = itemRepository.findById(itemId).get();
            List<OrderItem> orderItemList = orderItemRepository.findOrderItemByItem(item);
            List<ReviewFormDTO> reviewFormDTOList = new ArrayList<>();

            for (OrderItem orderItem : orderItemList) {
                List<Review> reviewList = reviewRepository.findReviewByOrderItem(orderItem);
                for (Review review : reviewList) {
                    if (review != null) {

                        ReviewFormDTO reviewFormDTO = ReviewFormDTO.builder()
                                .content(review.getContent())
                                .rate(review.getRate())
                                .reviewDate(review.getCreatedDate())
                                .orderDate(orderItem.getOrders().getOrderDate())
                                .orderItemName(item.getItemName())
                                .userName(review.getUser().getUserName())
                                .category(item.getCategory())
                                .uuidName(item.getImgUrl())
                                .build();

                        reviewFormDTOList.add(reviewFormDTO);
                    }
                }
            }

            map.put("reviews", reviewFormDTOList);
            map.put("count", reviewFormDTOList.size());

            return ResultCode.Success.result(map);
        } catch (Exception e) {
            return ResultCode.DB_ERROR.result();
        }

    }

    public Result<Review> addReview(ReviewFormDTO reviewFormDTO) {
        return addReview(reviewFormDTO.getUserId(), reviewFormDTO.getOrderItemId()
                , reviewFormDTO.getContent(), reviewFormDTO.getRate(), reviewFormDTO.getUuidName()
                , reviewFormDTO.getImg().getOriginalFilename());
    }

    public Result<Review> addReview(Long userId, Long orderItemId, String content, double rate, String uuidName, String originFileName) {
        try {

            Optional<User> optionalUser = userRepository.findById(userId);
            Optional<OrderItem> optionalOrderItem = orderItemRepository.findById(orderItemId);
            if (optionalOrderItem.isEmpty() && optionalUser.isEmpty()) {
                return ResultCode.USER_NOT_EXISTS.result();
            }
            User user = optionalUser.get();
            OrderItem orderItem = optionalOrderItem.get();
            orderItem.setReviewed(true);
            Review review = Review.builder()
                    .user(user)
                    .content(content)
                    .orderItem(orderItem)
                    .rate(rate)
                    .uuidName(uuidName)
                    .originalFileName(originFileName)
                    .build();

            reviewRepository.save(review);
            orderItemRepository.save(orderItem);
            return ResultCode.Success.result();

        } catch (Exception e) {
            return ResultCode.ETC_ERROR.result();
        }
    }

    @Value("${reviewPath}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public String storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();//사용자가 업로드한 파일이름
        String uuid = UUID.randomUUID().toString();
        int pos = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(pos + 1);
        String storeFileName = uuid + "." + ext;
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return storeFileName;
    }

    public Result<Review> deleteReview(Long reviewId) {

        try {
            Optional<Review> optionalReview = reviewRepository.findById(reviewId);
            if (optionalReview.isPresent()) {
                Review review = optionalReview.get();
                reviewRepository.delete(review);

                return ResultCode.Success.result();
            } else {
                return ResultCode.REVIEW_NOT_EXISTS.result();
            }
        } catch (Exception e) {
            return ResultCode.DB_ERROR.result();
        }


    }


}