import {useEffect, useState} from "react";
import {TProduct} from "@/lib/types.ts";
import ProductCard from "@/components/ProductCard.tsx";

function ProductList() {
  // const [products, setProducts] = useState<TProduct[]>([]);
  const [product, setProduct] = useState<TProduct | null>(null);

  const getProducts = async () => {
    try {
      const response = await fetch("http://127.0.0.1:8080/api/products/99");

      if (!response.ok)
        throw new Error("Fail to fetch products");

      const data = await response.json();
      setProduct(data);

    } catch (err) {
      console.error(err);
    }
  }

  useEffect(() => {
    getProducts().then();
  }, [])

  return (
    <div>{product ? <ProductCard product={product}/> : null}</div>
  );
}

export default ProductList;