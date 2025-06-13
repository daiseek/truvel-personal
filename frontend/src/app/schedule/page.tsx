"use client";

import React, { useState, useCallback } from "react";
import { ChevronLeft } from "lucide-react";
import * as S from "./stlyes";

// 타입 정의
interface CalendarProps {
  onDateSelect?: (dates: Date[]) => void;
  initialDates?: Date[];
  maxSelections?: number;
}

type WeekDay = "일" | "월" | "화" | "수" | "목" | "금" | "토";

interface DateButtonProps {
  date: Date;
  isSelected: boolean;
  isWeekend: boolean;
  isSelectable: boolean;
  onClick: (date: Date) => void;
}

// 날짜 버튼 컴포넌트
const DateButton: React.FC<DateButtonProps> = ({
  date,
  isSelected,
  isWeekend,
  isSelectable,
  onClick,
}) => {
  const handleClick = useCallback(() => {
    if (isSelectable) {
      onClick(date);
    }
  }, [date, isSelectable, onClick]);

  const day: number = date.getDate();

  return (
    <S.DateButtonStyled
      onClick={handleClick}
      disabled={!isSelectable}
      $isSelected={isSelected}
      $isWeekend={isWeekend}
      $isSelectable={isSelectable}
    >
      <S.DateNumber>{day}</S.DateNumber>
      {isSelected && <S.PulseEffect />}
    </S.DateButtonStyled>
  );
};

const TravelDatePicker: React.FC<CalendarProps> = ({
  onDateSelect,
  initialDates = [],
  maxSelections,
}) => {
  const [selectedDates, setSelectedDates] = useState<Date[]>(initialDates);

  // 현재 달부터 6개월간 표시
  const generateMonths = useCallback((): Date[] => {
    const months: Date[] = [];
    const currentDate: Date = new Date(2024, 11, 1); // 2024년 12월부터 시작

    for (let i = 0; i < 6; i++) {
      const month: Date = new Date(
        currentDate.getFullYear(),
        currentDate.getMonth() + i,
        1
      );
      months.push(month);
    }
    return months;
  }, []);

  const months: Date[] = generateMonths();

  const formatMonthYear = useCallback((date: Date): string => {
    return `${date.getFullYear()}년 ${String(date.getMonth() + 1).padStart(
      2,
      "0"
    )}월`;
  }, []);

  const getDaysInMonth = useCallback((date: Date): number => {
    return new Date(date.getFullYear(), date.getMonth() + 1, 0).getDate();
  }, []);

  const getFirstDayOfMonth = useCallback((date: Date): number => {
    return new Date(date.getFullYear(), date.getMonth(), 1).getDay();
  }, []);

  const isSameDate = useCallback((date1: Date, date2: Date): boolean => {
    return (
      date1.getFullYear() === date2.getFullYear() &&
      date1.getMonth() === date2.getMonth() &&
      date1.getDate() === date2.getDate()
    );
  }, []);

  const isDateSelected = useCallback(
    (date: Date): boolean => {
      return selectedDates.some((selectedDate: Date) =>
        isSameDate(selectedDate, date)
      );
    },
    [selectedDates, isSameDate]
  );

  const isWeekend = useCallback((date: Date): boolean => {
    const day: number = date.getDay();
    return day === 0 || day === 6; // 일요일(0) 또는 토요일(6)
  }, []);

  const handleDateClick = useCallback(
    (date: Date): void => {
      // 현재 달(1월)에서만 선택 가능하도록 제한
      if (date.getMonth() === 0 && date.getFullYear() === 2025) {
        if (isDateSelected(date)) {
          const newDates: Date[] = selectedDates.filter(
            (selectedDate: Date) => !isSameDate(selectedDate, date)
          );
          setSelectedDates(newDates);
          onDateSelect?.(newDates);
        } else {
          // maxSelections 체크
          if (maxSelections && selectedDates.length >= maxSelections) {
            return;
          }
          const newDates: Date[] = [...selectedDates, date];
          setSelectedDates(newDates);
          onDateSelect?.(newDates);
        }
      }
    },
    [selectedDates, isDateSelected, isSameDate, onDateSelect, maxSelections]
  );

  const renderCalendarGrid = useCallback(
    (monthDate: Date): React.ReactElement => {
      const daysInMonth: number = getDaysInMonth(monthDate);
      const firstDay: number = getFirstDayOfMonth(monthDate);
      const isCurrentMonth: boolean =
        monthDate.getMonth() === 0 && monthDate.getFullYear() === 2025; // 1월만 선택 가능

      // 빈 셀 생성
      const emptyCells: React.ReactElement[] = [];
      for (let i = 0; i < firstDay; i++) {
        emptyCells.push(<S.EmptyCell key={`empty-${i}`} />);
      }

      // 날짜 셀 생성
      const dateCells: React.ReactElement[] = [];
      for (let day = 1; day <= daysInMonth; day++) {
        const currentDate: Date = new Date(
          monthDate.getFullYear(),
          monthDate.getMonth(),
          day
        );
        const isSelected: boolean = isDateSelected(currentDate);
        const isWeekendDay: boolean = isWeekend(currentDate);
        const isSelectable: boolean = isCurrentMonth;

        dateCells.push(
          <DateButton
            key={day}
            date={currentDate}
            isSelected={isSelected}
            isWeekend={isWeekendDay}
            isSelectable={isSelectable}
            onClick={handleDateClick}
          />
        );
      }

      return (
        <S.DatesGrid>
          {emptyCells}
          {dateCells}
        </S.DatesGrid>
      );
    },
    [
      getDaysInMonth,
      getFirstDayOfMonth,
      isDateSelected,
      isWeekend,
      handleDateClick,
    ]
  );

  const handleComplete = useCallback((): void => {
    console.log("선택된 날짜들:", selectedDates);
    onDateSelect?.(selectedDates);
    // 선택 완료 로직
  }, [selectedDates, onDateSelect]);

  const weekDays: readonly WeekDay[] = [
    "일",
    "월",
    "화",
    "수",
    "목",
    "금",
    "토",
  ] as const;

  return (
    <S.Container>
      {/* 헤더 */}
      <S.Header>
        <S.HeaderContent>
          <S.BackButton>
            <ChevronLeft className="w-6 h-6 text-gray-600" />
          </S.BackButton>
          <S.Title>여행 날짜 선택</S.Title>
          <S.Spacer />
        </S.HeaderContent>
      </S.Header>

      {/* 스크롤 가능한 달력 영역 */}
      <S.ScrollableArea>
        <S.CalendarContainer>
          {months.map((month: Date, index: number) => (
            <S.MonthSection key={index}>
              {/* 월 타이틀 */}
              <S.MonthHeader>
                <S.MonthTitle>{formatMonthYear(month)}</S.MonthTitle>
              </S.MonthHeader>

              {/* 요일 헤더 */}
              <S.WeekDaysGrid>
                {weekDays.map((day: WeekDay, dayIndex: number) => (
                  <S.WeekDayCell key={day}>
                    <S.WeekDayText $dayIndex={dayIndex}>{day}</S.WeekDayText>
                  </S.WeekDayCell>
                ))}
              </S.WeekDaysGrid>

              {/* 날짜 그리드 */}
              {renderCalendarGrid(month)}
            </S.MonthSection>
          ))}
        </S.CalendarContainer>
      </S.ScrollableArea>

      {/* 하단 고정 버튼 */}
      <S.BottomFixedContainer>
        <S.CompleteButton
          disabled={selectedDates.length === 0}
          onClick={handleComplete}
          $hasSelections={selectedDates.length > 0}
        >
          {selectedDates.length > 0
            ? `${selectedDates.length}개 날짜 선택 완료`
            : "날짜를 선택해주세요"}
        </S.CompleteButton>
      </S.BottomFixedContainer>
    </S.Container>
  );
};

export default TravelDatePicker;
