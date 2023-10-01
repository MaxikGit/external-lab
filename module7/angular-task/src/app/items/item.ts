export interface Item {
  id: number,
  name: string,
  description: string,
  price: number,
  duration: number,
  createDate: Date,
  lastUpdateDate: Date,
  tags: string[],

  company?: string,
  longDescription?: string,
  image?: string,
}
