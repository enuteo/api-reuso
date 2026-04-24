import { AppLayout } from "@/components/app-layout";
import { Card } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Input } from "@/components/ui/input";
import {
  Table, TableBody, TableCell, TableHead, TableHeader, TableRow,
} from "@/components/ui/table";
import { Plus, Download, Filter, Search, Boxes, AlertTriangle, TrendingDown, PackageCheck, MoreHorizontal, ArrowUpRight, ArrowDownRight } from "lucide-react";

type Status = "in_stock" | "low" | "out";

const items: {
  sku: string; name: string; category: string; location: string;
  qty: number; min: number; price: number; status: Status; trend: number;
}[] = [
  { sku: "BRK-204",  name: "Brake Pad Set — Ceramic",     category: "Brakes",       location: "A-12",  qty: 84, min: 20, price: 42.5,  status: "in_stock", trend: 12 },
  { sku: "OIL-5W30", name: "Engine Oil 5W-30 (5L)",       category: "Lubricants",   location: "B-03",  qty: 9,  min: 15, price: 36.0,  status: "low",      trend: -8 },
  { sku: "FLT-AIR-22", name: "Air Filter — Universal",    category: "Filters",      location: "C-08",  qty: 47, min: 25, price: 14.9,  status: "in_stock", trend: 4 },
  { sku: "BAT-12V70", name: "Car Battery 12V 70Ah",       category: "Electrical",   location: "D-01",  qty: 0,  min: 5,  price: 129.0, status: "out",      trend: -100 },
  { sku: "TYR-205-55", name: "Tire 205/55 R16",           category: "Tires",        location: "E-Bay", qty: 32, min: 12, price: 88.0,  status: "in_stock", trend: 22 },
  { sku: "SPK-IRD-4", name: "Iridium Spark Plug ×4",      category: "Ignition",     location: "A-05",  qty: 6,  min: 10, price: 24.0,  status: "low",      trend: -3 },
  { sku: "WPR-22B",  name: "Wiper Blade 22\"",            category: "Exterior",     location: "C-14",  qty: 58, min: 20, price: 12.5,  status: "in_stock", trend: 9 },
  { sku: "COOL-G12", name: "Coolant G12+ (1L)",           category: "Lubricants",   location: "B-07",  qty: 23, min: 10, price: 9.9,   status: "in_stock", trend: 1 },
];

const statusMap: Record<Status, { label: string; cls: string }> = {
  in_stock: { label: "In stock", cls: "bg-success/10 text-success border-success/20" },
  low:      { label: "Low",      cls: "bg-warning/15 text-warning-foreground border-warning/30" },
  out:      { label: "Out",      cls: "bg-destructive/10 text-destructive border-destructive/20" },
};

const stats = [
  { label: "Total SKUs", value: "1,284", icon: Boxes,        delta: "+24 this week",    trend: "up"   as const },
  { label: "Stock value", value: "$184,920", icon: PackageCheck, delta: "+3.2%",         trend: "up"   as const },
  { label: "Low stock",  value: "12",     icon: TrendingDown,  delta: "+4 vs last week",   trend: "down" as const },
  { label: "Out of stock", value: "3",   icon: AlertTriangle, delta: "Action required",   trend: "down" as const },
];

const Stock = () => {
  return (
    <AppLayout
      title="Stock Management"
      description="Monitor inventory levels, track movement and replenish parts before they run out."
      actions={
        <>
          <Button variant="outline"><Download className="mr-2 h-4 w-4" /> Export</Button>
          <Button className="bg-primary text-primary-foreground hover:bg-secondary">
            <Plus className="mr-2 h-4 w-4" /> Add part
          </Button>
        </>
      }
    >
      {/* Stat cards */}
      <div className="grid gap-4 sm:grid-cols-2 lg:grid-cols-4 mb-6">
        {stats.map((s) => {
          const Up = s.trend === "up";
          return (
            <Card key={s.label} className="p-5 bg-surface-elevated border-border shadow-sm hover:shadow-elev transition-all">
              <div className="flex items-start justify-between">
                <div className="flex h-10 w-10 items-center justify-center rounded-lg bg-muted">
                  <s.icon className="h-5 w-5 text-accent" />
                </div>
                <span className={`inline-flex items-center text-xs font-medium ${Up ? "text-success" : "text-destructive"}`}>
                  {Up ? <ArrowUpRight className="h-3.5 w-3.5" /> : <ArrowDownRight className="h-3.5 w-3.5" />}
                </span>
              </div>
              <div className="mt-4">
                <div className="text-xs uppercase tracking-wider text-muted-foreground">{s.label}</div>
                <div className="mt-1 font-display text-3xl font-bold">{s.value}</div>
                <div className="mt-1 text-xs text-muted-foreground">{s.delta}</div>
              </div>
            </Card>
          );
        })}
      </div>

      {/* Table card */}
      <Card className="border-border bg-surface-elevated shadow-sm overflow-hidden">
        <div className="flex flex-wrap items-center gap-3 border-b border-border p-4">
          <div className="relative flex-1 min-w-[220px]">
            <Search className="absolute left-3 top-1/2 h-4 w-4 -translate-y-1/2 text-muted-foreground" />
            <Input placeholder="Search by SKU, name or category…" className="pl-9 h-10 bg-muted/40 border-transparent" />
          </div>
          <Button variant="outline" size="sm"><Filter className="mr-2 h-4 w-4" /> Category</Button>
          <Button variant="outline" size="sm"><Filter className="mr-2 h-4 w-4" /> Location</Button>
          <Button variant="outline" size="sm">Status: All</Button>
        </div>

        <div className="overflow-x-auto">
          <Table>
            <TableHeader>
              <TableRow className="bg-muted/40 hover:bg-muted/40">
                <TableHead className="w-[110px]">SKU</TableHead>
                <TableHead>Item</TableHead>
                <TableHead>Category</TableHead>
                <TableHead>Location</TableHead>
                <TableHead className="text-right">Qty</TableHead>
                <TableHead className="text-right">Min</TableHead>
                <TableHead className="text-right">Price</TableHead>
                <TableHead>Status</TableHead>
                <TableHead className="text-right">7d</TableHead>
                <TableHead className="w-[40px]"></TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {items.map((it) => {
                const s = statusMap[it.status];
                const trendUp = it.trend >= 0;
                return (
                  <TableRow key={it.sku} className="group">
                    <TableCell className="font-mono text-xs text-muted-foreground">{it.sku}</TableCell>
                    <TableCell className="font-medium">{it.name}</TableCell>
                    <TableCell className="text-muted-foreground">{it.category}</TableCell>
                    <TableCell>
                      <span className="inline-flex items-center rounded-md border border-border bg-muted/40 px-2 py-0.5 text-xs font-mono">
                        {it.location}
                      </span>
                    </TableCell>
                    <TableCell className="text-right font-display font-semibold">{it.qty}</TableCell>
                    <TableCell className="text-right text-muted-foreground">{it.min}</TableCell>
                    <TableCell className="text-right tabular-nums">${it.price.toFixed(2)}</TableCell>
                    <TableCell>
                      <Badge variant="outline" className={`font-medium ${s.cls}`}>{s.label}</Badge>
                    </TableCell>
                    <TableCell className={`text-right text-xs font-medium ${trendUp ? "text-success" : "text-destructive"}`}>
                      {trendUp ? "+" : ""}{it.trend}%
                    </TableCell>
                    <TableCell>
                      <Button variant="ghost" size="icon" className="h-8 w-8 opacity-0 group-hover:opacity-100">
                        <MoreHorizontal className="h-4 w-4" />
                      </Button>
                    </TableCell>
                  </TableRow>
                );
              })}
            </TableBody>
          </Table>
        </div>

        <div className="flex items-center justify-between border-t border-border p-4 text-sm text-muted-foreground">
          <span>Showing 8 of 1,284 items</span>
          <div className="flex gap-2">
            <Button variant="outline" size="sm" disabled>Previous</Button>
            <Button variant="outline" size="sm">Next</Button>
          </div>
        </div>
      </Card>
    </AppLayout>
  );
};

export default Stock;
