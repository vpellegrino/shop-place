CREATE TABLE public.products
(
    product_id serial NOT NULL,
    product_name text NOT NULL,
    unit_price numeric NOT NULL,
    CONSTRAINT product_pkey PRIMARY KEY (product_id)
);

CREATE TABLE public.orders
(
    order_id serial NOT NULL,
    buyer_email text NOT NULL,
    order_date date NOT NULL,
    CONSTRAINT order_pkey PRIMARY KEY (order_id)
);

CREATE TABLE public.order_details
(
    order_id integer NOT NULL,
    product_id integer NOT NULL,
    product_quantity numeric NOT NULL,
    unit_price numeric NOT NULL
);

ALTER TABLE public.order_details
ADD CONSTRAINT pk_order_detail
PRIMARY KEY(order_id, product_id);

ALTER TABLE public.order_details
ADD CONSTRAINT fk_detail_order
FOREIGN KEY (order_id) REFERENCES
public.orders (order_id);

ALTER TABLE public.order_details
ADD CONSTRAINT fk_detail_product
FOREIGN KEY (product_id) REFERENCES
public.products (product_id);
