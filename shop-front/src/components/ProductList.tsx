import {useEffect, useState} from "react";
import {TProduct} from "@/lib/types.ts";
import ProductCard from "@/components/ProductCard.tsx";

function ProductList() {
  const [products, setProducts] = useState<TProduct[]>([]);

  useEffect(() => {
    getProducts().then();
  }, [])

  const getProducts = async () => {
      try {
        const response = await fetch("http://localhost:7070/products/99");

        if (!response.ok)
          throw new Error("Fail to fetch products");

        const data = await response.json();
        setProducts(data);

      } catch (err) {
        console.error(err);
      }

    }

  return (
    <div>{products.map(product => <ProductCard product={product} />)}</div>
  );
}

export default ProductList;