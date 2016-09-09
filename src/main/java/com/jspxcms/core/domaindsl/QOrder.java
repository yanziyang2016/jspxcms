package com.jspxcms.core.domaindsl;

import static com.mysema.query.types.PathMetadataFactory.*;

import java.util.Date;

import com.jspxcms.core.domain.Order;
import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QUser is a Querydsl query type for Order
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = 719855465;

    private static final PathInits INITS = PathInits.DIRECT;

    public static final QOrder order = new QOrder("order");

    public final DateTimePath<java.util.Date> orderDate = createDateTime("orderDate", java.util.Date.class);


   


    public final NumberPath<Integer> id = createNumber("id", Integer.class);
    
    public final NumberPath<Integer> status = createNumber("status", Integer.class);
    
    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);
    
    public final StringPath orderNo = createString("orderNo");
    
    public final NumberPath<Integer> infoId = createNumber("infoId", Integer.class);
    public final NumberPath<Integer> infoPeriod = createNumber("infoPeriod", Integer.class);
    public final NumberPath<Integer> periodNo = createNumber("periodNo", Integer.class);
    public final StringPath logisticsNo = createString("logisticsNo");
    public final StringPath logisticsName = createString("logisticsName");

    
//	private Integer id;
//	private String orderNo;//订单号
//	private Integer userId;//用户ID
//	private Integer infoId;//商品id
//	private Integer infoPeriod;//商品期数
//	private Integer status;//订单状态
//	private Integer periodNo;//商品当期幸运序列
//	private String logisticsNo;//物流单号
//	private String logisticsName;//物流公司
//	private Date orderDate;
    



    public QOrder(String variable) {
        this(Order.class, forVariable(variable), INITS);
    }

    @SuppressWarnings("all")
    public QOrder(Path<? extends Order> path) {
        this((Class)path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QOrder(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QOrder(PathMetadata<?> metadata, PathInits inits) {
        this(Order.class, metadata, inits);
    }

    public QOrder(Class<? extends Order> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
    }

}

