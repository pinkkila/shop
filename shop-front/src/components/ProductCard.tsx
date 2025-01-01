import {Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle} from "@/components/ui/card.tsx";
import {TProduct} from "@/lib/types.ts";

function ProductCard({product}: {product: TProduct}) {
  return (
    <Card>
      <CardHeader>
        <CardTitle>{product.productName}</CardTitle>
        <CardDescription>Card Description</CardDescription>
      </CardHeader>
      <CardContent>
        <p>Card Content</p>
      </CardContent>
      <CardFooter>
        <p>Card Footer</p>
      </CardFooter>
    </Card>

  );
}

export default ProductCard;